# Final-Project
Human Final
___파이널 프로젝트의 완성본을 올려두는 main branch 입니다.___
***
### MES 스마트폰 제조 공정 관리
## 📑 목차
1.[프로젝트 개요](#프로젝트-개요)<br>
2.[개발기간](#개발기간)<br>
3.[프로젝트 소개](#프로젝트-소개)<br>
4.[개발환경 및 도구](#개발환경-및-도구)<br>
5.[멤버](#멤버-구성)<br>
6.[주요기능](#main-functions)<br>
7.[아키텍처](#아키텍처)<br>
8.[ERD](#ERD)
<br><br>
## 프로젝트 개요
### 개발기간
+ 2025년 3월 24일 ~ 2025년 4월 16일
### 프로젝트 소개
+ 스마트폰 제조 공정에서 사용하는 MVC 모델 설계
+ 프로젝트 일정 관리, GEMINI 사용을 통한 사용자 편의 개선, 차트 활용
+ 엑셀 및 CSV, txt 파일을 통한 업로드 및 다운로드
+ 로그인 인증가 인가 기능으로 권한 설정
+ DB 트리거 및 외래키 활용을 통한 데이터 구조화
+ 데이터 정규화 과정을 통해 효율적인 데이터 관리
+ 유니버설 디자인으로 모든 사용자에게 친화적인 모델 만들기
+ AGILE 방법론을 통한 효율적인 프로젝트 일정관리
### 개발환경 및 도구
+ JAVA 11, C#
+ IDE: Intellij, Visual Studio
+ Framwork: SpringBoot(2.7.3)
+ DataBase: MariaDB
+ DevOps: Git, GitHub
+ Tools: ChatGPT, Claude, Copliot
### 멤버 구성
<table>
  <tr>
    <td>이름</td>
    <td>담당업무</td>
  </tr>
  <tr>
    <td>김재훈(팀장)</td>
    <td>일정관리, 발표, DB관리, 완제품/원자재 제품관리, 재고, 코드관리 페이지 개발, 로그인 권한 관리</td>
  </tr>
  <tr>
    <td>구교진(팀원)</td>
    <td>직원관리, 로그인, 메인 페이지 개발, 일정관리, 페이지 디자인 관리, 보안설정</td>
  </tr>
  <tr>
    <td>전유범(팀원)</td>
    <td>가상공장관리(C#), 공장 관리, 메인 페이지 개발, 페이지 디자인 관리, API(GEMINI), 팀 전체 개발 보조</td>
  </tr>
  <tr>
    <td>정지연(팀원)</td>
    <td>고객/거래처 관리, 주문 페이지 개발, 엑셀 및 CSV, txt 파일 다운로드 기능 개발, 지도 API(NAVER), 페이지 디자인 관리</td>
  </tr>
  <tr>
    <td>한소중(팀원)</td>
    <td>완제품/원자재 입/출고 관리 페이지, 완제품 레시피 관리, 코드 모듈화 및 관리 편집 담당, 프로젝트 QM</td>
  </tr>
</table>
<h3 id="main-functions">주요 기능</h3>
<details>
  <summary><b>로그인[구교진, 김재훈]</b></summary>
  <br>
  <h3> - 로그인 페이지 : 로그인하지 않으면 다른 페이지 접근 불가, 특정 권한을 가진 ID만 등록, 수정, 삭제 불가 </h3>
  <br>
  <img src ="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/login.JPG" />
</details>
<details>
  <summary><b>대시보드[구교진, 전유범]</b></summary>
  <br>
  <h3> - 대시보드 : 사용자 친화적인 UX/UI 디자인</h3>
  <br>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/dashboard.JPG" />
  <br><br>
  <h3> - 사이드바 : 다크모드, 직관적인 디자인과 분류</h3>
  <br>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/dashboard_sidebar.JPG" />
  <br>
  <h3> - 챗봇 : GEMINI API를 활용한 웹페이지 도우미</h3>
  <br>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/dashboard_chatbot.JPG" />
  <p> - 각 페이지 설명을 위한 프롬프트 작성, 페이지별 설명을 제외한 다른 설명 간략화</p>
</details>
<details>
  <summary><b>Raw Material[김재훈, 한소중]</b></summary>
  <br>
  <details>
    <summary>Raw Materials[김재훈]</summary>
    <br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterials.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterial_register.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterial_edit.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterial_delete.JPG" />
  </details>
  <details>
    <summary>Stock[김재훈]</summary>
    <br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialStock.JPG" />
  </details>
  <details>
    <summary>Inbound[한소중]</summary>
    <br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound_register.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound_edit.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound_delete.JPG" />
  </details>
  <details>
    <summary>Outbound[한소중]</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialOutbound.JPG" />
  </details>
</details>
<details>
  <summary><b>Finished Product[김재훈, 한소중]</b></summary>
  <br>
  <details>
    <summary>Finished Products[김재훈]</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProducts.JPG" />
  </details>
  <details>
    <summary>Stock[김재훈]</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProductStock.JPG" />
  </details>
  <details>
    <summary>Inbound[한소중]</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProductInbound.JPG" />
  </details>
  <details>
    <summary>Outbound[한소중]</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/blob/raw/finalProject_img/finishedProductOutbound.JPG" />
  </details>
</details>
<details>
  <summary><b>CodeManagement[김재훈]</b></summary>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/codeManagement.JPG" />
</details>
<details>
  <summary><b>Eployees[구교진]</b></summary>
  <img scr="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employees.JPG" />
  <img scr="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employee_register.JPG" />
  <img scr="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employee_edit.JPG" />
  <img scr="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employee_delete.JPG" />
</details>
<details>
  <summary><b>Customer[정지연]</b></summary>
  <br>
  <details>
    <summary>Customers</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customers.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_api.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/CSVupload_download.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_register.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_edit.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_delete.JPG" />
  </details>
  <details>
    <summary>CustomerOrders</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customerOrders.JPG" />
  </details>
</details>
<details>
  <summary><b>Supplier[정지연]</b></summary>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/suppliers.JPG" />
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/supplier_register.JPG" />
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/supplier_edit.JPG" />
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/supplier_delete.JPG" />
</details>
<details>
  <summary><b>Factory(Machine)[전유범]<b></summary>
    <br>
  <details>
    <summary>Info</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_info.JPG" />
  </details>
  <details>
    <summary>Chart</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_chart.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_chart2.JPG" />
  </details>
  <details>
    <summary>History</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_history.JPG" />
  </details>
</details>
