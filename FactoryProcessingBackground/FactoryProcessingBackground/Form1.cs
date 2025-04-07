using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using MySql.Data;

namespace FactoryProcessingBackground
{
    public partial class Form1 : Form
    {
        // C# 데이터베이스 연결 설정
        string connectionString = "Server=1.220.247.78;Port=3308;Database=final2410_1;User=final_2410_team1;Password=1234;";

        private Timer timer;

        private Boolean machineOn = false;

        // Line_id별로 machine들을 그룹화
        Dictionary<string, List<DataRow>> lineMachineGroups = new Dictionary<string, List<DataRow>>();

        // 생성자
        public Form1()
        {
            InitializeComponent();

            string query = "SELECT a.* " +
                           "FROM machine_history a " +
                           "JOIN (" +
                           "SELECT machine_id, MAX(production_date) AS latest_date " +
                           "FROM machine_history " +
                           "GROUP BY machine_id" +
                           ") b " +
                           "ON a.machine_id = b.machine_id AND DATE(a.production_date) = DATE(b.latest_date)";

            using (MySqlConnection conn = new MySqlConnection(connectionString))
            {
                try
                {
                    conn.Open();

                    DataTable machineHistoryTable = new DataTable();
                    using (MySqlDataAdapter adapter = new MySqlDataAdapter(query, conn))
                    {
                        adapter.Fill(machineHistoryTable);
                    }

                    foreach (DataRow row in machineHistoryTable.Rows)
                    {
                        string machineId = row["machine_id"].ToString();
                        string lineId = machineId.Split('_')[0]; // Line 추출

                        if (!lineMachineGroups.ContainsKey(lineId))
                        {
                            lineMachineGroups[lineId] = new List<DataRow>();
                        }
                        lineMachineGroups[lineId].Add(row);
                    }

                    Random rand = new Random(); // 랜덤 객체

                    foreach (var lineGroup in lineMachineGroups)
                    {
                        string lineId = lineGroup.Key;
                        List<DataRow> machines = lineGroup.Value;

                        // P1 -> P2 -> P3 순서대로 정렬
                        machines.Sort((a, b) => a["machine_id"].ToString().CompareTo(b["machine_id"].ToString()));

                        int previousProduction = 0;

                        for (int i = 0; i < machines.Count; i++)
                        {
                            DataRow row = machines[i];

                            if ((Convert.ToDateTime(row["production_date"]).Date) != DateTime.Today)
                            {
                                int dayDiff = (DateTime.Today - (Convert.ToDateTime(row["production_date"]).Date)).Days;

                                //MessageBox.Show(dayDiff.ToString());

                                for (int j = 1; j < dayDiff; j++)
                                {
                                    int dayProduction, dayDefective;

                                    if (machines.IndexOf(row) == 0) // P1
                                    {
                                        int randNum = rand.Next(0, 2);
                                        dayProduction = 500 - randNum;
                                    }
                                    else if (machines.IndexOf(row) == machines.Count - 1) // 마지막 P
                                    {
                                        dayProduction = previousProduction;

                                        string selectProductQuery = "SELECT product_id FROM machine_info WHERE machine_id = @machine_id";
                                        string productId = null;

                                        using (MySqlCommand cmd = new MySqlCommand(selectProductQuery, conn))
                                        {
                                            cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                            object result = cmd.ExecuteScalar();
                                            if (result != null && result != DBNull.Value)
                                            {
                                                productId = result.ToString(); // product_id 저장
                                            }
                                        }

                                        //  finished_product_production 업데이트
                                        string getProductCodeQuery = "SELECT product_code FROM finished_products WHERE product_id = @product_id";
                                        string productCode = null;

                                        using (MySqlCommand cmdGetCode = new MySqlCommand(getProductCodeQuery, conn))
                                        {
                                            cmdGetCode.Parameters.AddWithValue("@product_id", productId);
                                            object codeResult = cmdGetCode.ExecuteScalar();
                                            if (codeResult != null && codeResult != DBNull.Value)
                                            {
                                                productCode = codeResult.ToString();
                                            }
                                        }

                                        // 변환된 product_code가 있을 경우에만 업데이트
                                        if (!string.IsNullOrEmpty(productCode))
                                        {
                                            string updateFinishedQuery = "UPDATE finished_product_production " +
                                                                        "SET quantity = @quantity " +
                                                                        "WHERE product_code = @product_code";

                                            using (MySqlCommand cmd = new MySqlCommand(updateFinishedQuery, conn))
                                            {
                                                cmd.Parameters.AddWithValue("@quantity", dayProduction);
                                                cmd.Parameters.AddWithValue("@product_code", productCode);
                                                cmd.ExecuteNonQuery();
                                            }
                                        }
                                    }
                                    else // P2, P3...
                                    {
                                        double reductionRate = (rand.NextDouble() * 0.009) + 0.001; // 0.1% ~ 1% 감소
                                        dayProduction = (int)(previousProduction * (1 - reductionRate));
                                    }

                                    dayDefective = rand.Next(0, 2);
                                    previousProduction = dayProduction;

                                    // machine_history 삽입
                                    string insertQuery = "INSERT INTO machine_history (machine_id, production_amount, defective_amount, production_date, production_date_update) " +
                                                         "VALUES (@machine_id, @production_amount, @defective_amount, @production_date, @production_date_update)";

                                    using (MySqlCommand cmd = new MySqlCommand(insertQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        cmd.Parameters.AddWithValue("@production_amount", dayProduction);
                                        cmd.Parameters.AddWithValue("@defective_amount", dayDefective);
                                        cmd.Parameters.AddWithValue("@production_date", Convert.ToDateTime(row["production_date"]).AddDays(j));
                                        cmd.Parameters.AddWithValue("@production_date_update", Convert.ToDateTime(row["production_date"]).AddDays(j));

                                        cmd.ExecuteNonQuery();
                                    }

                                    // 원자재 소모 처리
                                    string selectConsumeQuery = "SELECT * FROM machine_raw_material_consume WHERE machine_id = @machine_id";
                                    DataTable machineConsumeTable = new DataTable();

                                    using (MySqlCommand cmd = new MySqlCommand(selectConsumeQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        using (MySqlDataAdapter adapter = new MySqlDataAdapter(cmd))
                                        {
                                            adapter.Fill(machineConsumeTable);
                                        }
                                    }

                                    foreach (DataRow consumeRow in machineConsumeTable.Rows)
                                    {
                                        string selectReserveQuery = "SELECT stock FROM machine_raw_material_reserve " +
                                                                    "WHERE machine_id = @machine_id AND material_code = @material_code";
                                        int currentStock = 0;

                                        using (MySqlCommand cmd = new MySqlCommand(selectReserveQuery, conn))
                                        {
                                            cmd.Parameters.AddWithValue("@machine_id", consumeRow["machine_id"].ToString());
                                            cmd.Parameters.AddWithValue("@material_code", consumeRow["material_code"].ToString());

                                            object result = cmd.ExecuteScalar();
                                            if (result != null && result != DBNull.Value)
                                            {
                                                currentStock = Convert.ToInt32(result);
                                            }
                                        }

                                        int updatedStock = currentStock - Convert.ToInt32(consumeRow["quantity"]) * (dayProduction + dayDefective);
                                        updatedStock = Math.Max(0, updatedStock);

                                        string modifyMachineReserveQuery = "UPDATE machine_raw_material_reserve " +
                                                                            "SET stock = @stock " +
                                                                            "WHERE machine_id = @machine_id " +
                                                                            "AND material_code = @material_code";

                                        using (MySqlCommand cmd = new MySqlCommand(modifyMachineReserveQuery, conn))
                                        {
                                            cmd.Parameters.AddWithValue("@stock", updatedStock);
                                            cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                            cmd.Parameters.AddWithValue("@material_code", consumeRow["material_code"].ToString());

                                            cmd.ExecuteNonQuery();
                                        }
                                    }
                                }
                            }

                            // 오늘 날짜 데이터가 없으면, machine_id별로 production_amount와 defective_amount가 0인 데이터를 삽입
                            string checkTodayQuery = "SELECT COUNT(*) FROM machine_history WHERE machine_id = @machine_id AND DATE(production_date) = @production_date";
                            using (MySqlCommand cmd = new MySqlCommand(checkTodayQuery, conn))
                            {
                                cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                cmd.Parameters.AddWithValue("@production_date", DateTime.Today.ToString("yyyy-MM-dd"));

                                int count = Convert.ToInt32(cmd.ExecuteScalar());

                                if (count == 0)
                                {
                                    string insertZeroDataQuery = "INSERT INTO machine_history (machine_id, production_amount, defective_amount, production_date, production_date_update) " +
                                                                 "VALUES (@machine_id, 0, 0, @production_date, @production_date_update)";

                                    using (MySqlCommand insertCmd = new MySqlCommand(insertZeroDataQuery, conn))
                                    {
                                        insertCmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        insertCmd.Parameters.AddWithValue("@production_date", DateTime.Today);
                                        insertCmd.Parameters.AddWithValue("@production_date_update", DateTime.Today);

                                        insertCmd.ExecuteNonQuery();
                                    }
                                }
                            }
                        }
                    }
                    string getAllMachinesQuery = "SELECT machine_id FROM machine_info";
                    DataTable allMachinesTable = new DataTable();

                    using (MySqlDataAdapter adapter = new MySqlDataAdapter(getAllMachinesQuery, conn))
                    {
                        adapter.Fill(allMachinesTable);
                    }

                    foreach (DataRow machineRow in allMachinesTable.Rows)
                    {
                        string machineId = machineRow["machine_id"].ToString();
                        string lineId = machineId.Split('_')[0]; // Line 추출

                        // machine_history에 해당 머신의 데이터가 있는지 확인
                        string checkHistoryQuery = "SELECT COUNT(*) FROM machine_history WHERE machine_id = @machine_id";
                        bool hasHistory = false;

                        using (MySqlCommand cmd = new MySqlCommand(checkHistoryQuery, conn))
                        {
                            cmd.Parameters.AddWithValue("@machine_id", machineId);
                            int count = Convert.ToInt32(cmd.ExecuteScalar());
                            hasHistory = (count > 0);
                        }

                        // 히스토리가 없는 머신은 오늘 날짜 데이터 생성
                        if (!hasHistory)
                        {
                            string insertTodayQuery = "INSERT INTO machine_history (machine_id, production_amount, defective_amount, production_date, production_date_update) " +
                                                     "VALUES (@machine_id, 0, 0, @production_date, @production_date_update)";

                            using (MySqlCommand insertCmd = new MySqlCommand(insertTodayQuery, conn))
                            {
                                insertCmd.Parameters.AddWithValue("@machine_id", machineId);
                                insertCmd.Parameters.AddWithValue("@production_date", DateTime.Today);
                                insertCmd.Parameters.AddWithValue("@production_date_update", DateTime.Today);

                                insertCmd.ExecuteNonQuery();
                            }

                            // lineMachineGroups에 추가
                            if (!lineMachineGroups.ContainsKey(lineId))
                            {
                                lineMachineGroups[lineId] = new List<DataRow>();
                            }

                            // 새로 만든 히스토리 데이터 가져오기
                            string getNewRowQuery = "SELECT * FROM machine_history WHERE machine_id = @machine_id AND DATE(production_date) = @production_date";
                            DataTable newRowTable = new DataTable();

                            using (MySqlCommand getCmd = new MySqlCommand(getNewRowQuery, conn))
                            {
                                getCmd.Parameters.AddWithValue("@machine_id", machineId);
                                getCmd.Parameters.AddWithValue("@production_date", DateTime.Today.ToString("yyyy-MM-dd"));

                                using (MySqlDataAdapter getAdapter = new MySqlDataAdapter(getCmd))
                                {
                                    getAdapter.Fill(newRowTable);

                                    if (newRowTable.Rows.Count > 0)
                                    {
                                        lineMachineGroups[lineId].Add(newRowTable.Rows[0]);
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("연결 실패: " + ex.Message);
                }
            }

            // Timer 초기화
            timer = new Timer();
            timer.Interval = 15000; // 1000ms = 1초
            timer.Tick += Run; //  이벤트 핸들러 연결
            timer.Start(); // 타이머 시작

            ProductionSpeedControl.Value = timer.Interval;
        }

        // 15초마다 실행되는 함수
        private void Run(object sender, EventArgs e)
        {
            if (machineOn)
            {
                using (MySqlConnection conn = new MySqlConnection(connectionString))
                {
                    try
                    {
                        conn.Open();  // MariaDB 연결 시도

                        // 각 라인 그룹에 대해 처리
                        foreach (var lineGroup in lineMachineGroups)
                        {
                            string lineId = lineGroup.Key;
                            List<DataRow> machines = lineGroup.Value;

                            // P1 -> P2 -> P3 순으로 정렬
                            machines.Sort((a, b) => a["machine_id"].ToString().CompareTo(b["machine_id"].ToString()));

                            bool pass = true;  // 머신 통과여부
                            int product_id = 0;

                            // 각 머신을 순차적으로 처리
                            foreach (DataRow row in machines)
                            {
                                double passChance = 0.99;  // 99% 확률로 통과

                                string selectConsumeQuery = "SELECT material_code, quantity FROM machine_raw_material_consume WHERE machine_id = @machine_id";
                                DataTable consumeTable = new DataTable();

                                using (MySqlCommand cmd = new MySqlCommand(selectConsumeQuery, conn))
                                {
                                    cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                    using (MySqlDataAdapter adapter = new MySqlDataAdapter(cmd))
                                    {
                                        adapter.Fill(consumeTable);
                                    }
                                }

                                foreach (DataRow consumeRow in consumeTable.Rows)
                                {
                                    int consumeQuantity = Convert.ToInt32(consumeRow["quantity"]);

                                    // machine_raw_material_reserve 테이블에서 원자재의 현재 재고를 조회
                                    string selectStockQuery = "SELECT stock FROM machine_raw_material_reserve WHERE machine_id = @machine_id AND material_code = @material_code";
                                    int currentStock = 0;

                                    using (MySqlCommand cmd = new MySqlCommand(selectStockQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        cmd.Parameters.AddWithValue("@material_code", consumeRow["material_code"].ToString());
                                        object result = cmd.ExecuteScalar();
                                        if (result != null && result != DBNull.Value)
                                        {
                                            currentStock = Convert.ToInt32(result);
                                        }
                                    }

                                    // 재고 차감 계산 (소모된 양만큼)
                                    int updatedStock = currentStock - consumeQuantity;
                                    updatedStock = Math.Max(0, updatedStock);  // 재고가 음수가 되지 않도록 설정

                                    // machine_raw_material_reserve 테이블에서 재고 업데이트
                                    string updateReserveQuery = "UPDATE machine_raw_material_reserve " +
                                                                "SET stock = @stock " +
                                                                "WHERE machine_id = @machine_id AND material_code = @material_code";

                                    using (MySqlCommand cmd = new MySqlCommand(updateReserveQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@stock", updatedStock);
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        cmd.Parameters.AddWithValue("@material_code", consumeRow["material_code"].ToString());

                                        cmd.ExecuteNonQuery();
                                    }
                                }

                                if (RandomChance(passChance))
                                {
                                    pass = true;

                                    string modifyHistoryQuery = "UPDATE machine_history " +
                                                                "SET production_amount = production_amount + 1 " +
                                                                "WHERE machine_id = @machine_id " +
                                                                "AND DATE(production_date) = @production_date";

                                    using (MySqlCommand cmd = new MySqlCommand(modifyHistoryQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        cmd.Parameters.AddWithValue("@production_date", DateTime.Today.ToString("yyyy-MM-dd"));

                                        cmd.ExecuteNonQuery();
                                    }

                                    string selectProductQuery = "SELECT product_id FROM machine_info WHERE machine_id = @machine_id";

                                    using (MySqlCommand cmd = new MySqlCommand(selectProductQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        object result = cmd.ExecuteScalar();
                                        if (result != null && result != DBNull.Value)
                                        {
                                            product_id = Convert.ToInt32(result); // product_id 저장
                                        }
                                    }
                                }
                                else
                                {
                                    pass = false;

                                    string modifyHistoryQuery = "UPDATE machine_history " +
                                                                "SET defective_amount = defective_amount + 1 " +
                                                                "WHERE machine_id = @machine_id " +
                                                                "AND DATE(production_date) = @production_date";

                                    using (MySqlCommand cmd = new MySqlCommand(modifyHistoryQuery, conn))
                                    {
                                        cmd.Parameters.AddWithValue("@machine_id", row["machine_id"].ToString());
                                        cmd.Parameters.AddWithValue("@production_date", DateTime.Today.ToString("yyyy-MM-dd"));

                                        cmd.ExecuteNonQuery();
                                    }

                                    break;
                                }
                            }

                            if (pass)
                            {
                                ProcessLastRow(product_id);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("연결 실패: " + ex.Message);
                    }
                }
            }
        }

       
        private static readonly Random rand = new Random();
        // 99% 확률로 true/false를 반환하는 함수
        private bool RandomChance(double chance)
        {
            double roll = rand.NextDouble();
            return roll <= chance;
        }

        // 마지막 로우 처리하는 함수
        private void ProcessLastRow(int product_id)
        {
            if(product_id != 0)
            {
                string getProductCodeByProductId = "SELECT product_code FROM finished_products WHERE product_id = @product_id";
                string product_code = null;

                string updateFinishedQuery = "UPDATE finished_product_production " +
                                             "SET quantity = quantity + 1 " +
                                             "WHERE product_code = @product_code";

                using (MySqlConnection conn = new MySqlConnection(connectionString))
                {
                    try
                    {
                        conn.Open();

                        using (MySqlCommand cmd = new MySqlCommand(getProductCodeByProductId, conn))
                        {
                            cmd.Parameters.AddWithValue("@product_id", product_id);
                            object result = cmd.ExecuteScalar();
                            if (result != null && result != DBNull.Value)
                            {
                                product_code = result.ToString();
                            }
                        }

                        if (!string.IsNullOrEmpty(product_code))
                        {
                            using (MySqlCommand cmd = new MySqlCommand(updateFinishedQuery, conn))
                            {
                                cmd.Parameters.AddWithValue("@product_code", product_code);
                                cmd.ExecuteNonQuery();
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("연결 실패: " + ex.Message);
                    }
                }   
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            machineOn = true;
        }

        private void ProductionSpeedControl_ValueChanged(object sender, EventArgs e)
        {
            timer.Interval = (int)ProductionSpeedControl.Value;
        }
    }
}