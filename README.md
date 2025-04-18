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
6.[주요기능](#주요-기능)<br>
7.[아키텍처](#아키텍처)<br>
8.[ERD](#ERD)
<br><br>
## 프로젝트 개요
## 개발기간
+ 2025년 3월 24일 ~ 2025년 4월 16일
## 프로젝트 소개
+ 스마트폰 제조 공정에서 사용하는 MVC 모델 설계
+ 프로젝트 일정 관리, GEMINI 사용을 통한 사용자 편의 개선, 차트 활용
+ 엑셀 및 CSV, txt 파일을 통한 업로드 및 다운로드
+ 로그인 인증가 인가 기능으로 권한 설정
+ DB 트리거 및 외래키 활용을 통한 데이터 구조화
+ 데이터 정규화 과정을 통해 효율적인 데이터 관리
+ 유니버설 디자인으로 모든 사용자에게 친화적인 모델 만들기
+ AGILE 방법론을 통한 효율적인 프로젝트 일정관리
## 개발환경 및 도구
+ JAVA 11, C#
+ IDE: Intellij, Visual Studio
+ Framwork: SpringBoot(2.7.3)
+ DataBase: MariaDB
+ DevOps: Git, GitHub
+ Tools: ChatGPT, Claude, Copliot
## 멤버 구성
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

## 주요 기능

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
  <p> - modal을 이용하여 구현하고 채팅내용이 길어지거나 화면상 내용을 참고하기 위하여 모달 이동기능을 구현</p>
  <br>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/dashboard_chatbot.JPG" />
  <p> - 각 페이지 설명을 위한 프롬프트 작성, 페이지별 설명을 제외한 다른 설명 간략화</p>
</details>
<details>
  <summary>🔸 <b>Raw Material[김재훈, 한소중]</b></summary>
  <br>
  <details>
    <summary>  └ Raw Materials[김재훈]</summary>
    <br>
    <h3> - 원자재 관리 : 원자재에 대한 정보를 관리하는 페이지이다.</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterials.JPG" />
	  <p> - 원자재에 대한 정보를 관리하여 어떠한 원자재를 갖고 있는지 확인하는 페이지</p>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterial_register.JPG" />
	  <p> - 등록시 원자재 코드와 이름 카테코리, 포장단위(ex, U001 이러식으로 등록하여 codeManagement에서 관리), 설명을 등록</p>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterial_edit.JPG" />
	  <p> - 기존에 등록한 요소들을 수정하는 기능</p>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterial_delete.JPG" />
	  <p> - 데이터를 삭제하는 기능</p>
  </details>
  <details>
    <summary>  └ Stock[김재훈]</summary>
    <h3> - 원자재 재고 : 재고에 대한 등록은 자동으로 등록, 수정, 삭제 기능 없이 DB정보만 보여준다.</h3>
    <br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialStock.JPG" />
    <p> - 원자재 관리 페이지에서 등록시 현재 재고 0 으로 자동 등록</p>
    <p> - 원자재 입고 페이지에서 Status가 Complete로 등록시 현재 재고에 + 되도록 DB에서 트리거 작동</p>
    <p> - 원자재 출고 페이지에서 Status가 Complete로 등록시 현재 재고에 - 되도록 DB에서 트리거 작동</p>
  </details>
  <details>
    <summary>  └ Inbound[한소중]</summary>
    <br>
    <h3> - 원자재입고 : MaterialId, SupplierId 와 연동하여 상호보완, CRUD 를 api 형식으로 구현하며,
      <br>수정/삭제 버튼은 로그인 시 관리자 ID에만 노출된다.</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound_register.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound_edit.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialInbound_delete.JPG" />
  </details>
  <details>
    <summary>  └ Outbound[한소중]</summary>
    <h3> - 원자재입고 : MaterialId, SupplierId 와 연동하여 상호보완, CRUD 를 api 형식으로 구현하며,
      <br>수정/삭제 버튼은 로그인 시 관리자 ID에만 노출된다.</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/rawMaterialOutbound.JPG" />
  </details>
</details>
<details>
  <summary><b>Finished Product[김재훈, 한소중]</b></summary>
  <br>
  <details>
    <summary>Finished Products[김재훈]</summary>
	  <h3> - 완제품 관리 : 현재 만들어지는 제품에 대한 페이지</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProducts.JPG" />
	  <p> - 원자재와 마찬가지로 현재 만들어지는 제품에 대한 관리와 등록, 수정, 삭제 기능 </p>
  </details>
  <details>
    <summary>Stock[김재훈]</summary>
    <h3> - 완제품 재고 : 재고에 대한 등록은 자동으로 등록, 수정, 삭제 기능 없이 DB정보만 보여준다.</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProductStock.JPG" />
    <p> - 완제품 관리 페이지에서 등록시 현재 재고 0 으로 자동 등록</p>
    <p> - 완제품 입고 페이지에서 Status가 Complete로 등록시 현재 재고에 + 되도록 DB에서 트리거 작동</p>
    <p> - 완제품 출고 페이지에서 Status가 Complete로 등록시 현재 재고에 - 되도록 DB에서 트리거 작동</p>
  </details>
  <details>
    <summary>Inbound[한소중]</summary>
    <h3> - 완제품 입고 : ProductId, SupplierId 와 연동하여 상호보완, CRUD 를 api 형식으로 구현하며,
      <br>수정/삭제 버튼은 로그인 시 관리자 ID에만 노출된다.</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProductInbound.JPG" />
  </details>
  <details>
    <summary>Outbound[한소중]</summary>
    <h3> - 완제품 입고 : ProductId, SupplierId 와 연동하여 상호보완, CRUD 를 api 형식으로 구현하며,
      <br>수정/삭제 버튼은 로그인 시 관리자 ID에만 노출된다.</h3>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/finishedProductOutbound.JPG" />
  </details>
</details>
<details>
  <summary><b>CodeManagement[김재훈]</b></summary>
	<h3> - 포장코드 관리 페이지 : 원자재와, 완제품에 사용되는 포장 유닛을 코드화 하여 관리하는 페이지</h3>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/codeManagement.JPG" />
	<p> - 각 코드에 맞는 정보를 입력해 더 편하게 원자재 및 완제품을 관리한다.</p>
  <img src="" />
	<p> - 등록시 (P)Search 완제품에서 검색, (M)Search 원자재에서 검색 을 통해 다른 코드를 입력하는 실수를 방지한다.</p>
</details>
<details>
  <summary><b>Eployees[구교진]</b></summary>
  <br>
  <h3> - 사원관리 : 검색기능, CRUD, 전체 엑셀,CSV 및 검색별 엑셀, CSV 다운로드 기능</h3>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employees.JPG" />
  <h3> - 등록 : 등록시 로그인 아이디, 비밀번호 자동생성[+김재훈]</h3>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employee_register.JPG" />
  <h3> - 수정 : 각 컬럼별 수정, 퇴사시 퇴사 날짜 업데이트</h3>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employee_edit.JPG" />
  <h3> - 삭제 : 삭제 시 정보 및 로그인 기능 삭제</h3>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/employee_delete.JPG" />
</details>
<details>
  <summary><b>Customer[정지연]</b></summary>
  <br>
  <details>
    <summary>Customers</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customers.JPG" />
	<p> - 등록,업로드(CSV),다운로드(전체 엑셀, 전체 CSV, 검색결과 엑셀, 검색결과 CSV), 페이지 당 row(perPage), 기본 테이블, 컬럼명 클릭시 정렬</p>
	<br><br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_api.JPG" />
	<p> - 테이블에 주소값이 있고, NCloud maps와 mapping되는 주소값인 경우 accordion에 주소 표시(geocode, dynamic map)</p>
 	<br><br>	  
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/CSVupload_download.JPG" />
	<p> - CSV 업로드로 등록 또는 수정을 위한 샘플 CSV(@Id의 값 없이 등록하면 신규 등록 / 수정할 @Id의 값을 확인 후 입력하면 해당 row 값 수정)</p>
	<br><br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_register.jpg" />
	<p> - modal 통한 등록창</p>
	<br><br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_edit.jpg" />
	<p> - modal 통한 수정(해당 row 클릭시 확인되는 accordion에서 수정 가능)</p>
	<br><br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_delete.jpg" />
	<p> - modal 통한 삭제(해당 row 클릭시 확인되는 accordion에서 삭제 가능)</p>
	<br><br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_search.jpg" />
	<p> - 각 컬럼당 검색 박스에 검색어 입력 후 Enter, 검색된 상태에서 다른 컬럼 검색 입력하면 AND 검색 가능</p>
	<br><br>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customer_search_excel.jpg" />
	<p> - 검색결과 다운로드의 경우 화면에 보이는 페이지로 검색되므로, 전체가 필요하면 perPage를 수정하여 다운로드</p>    
  </details>
  <details>
    <summary>CustomerOrders</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/customerOrders.JPG" />
	<p> - customer와 동일한 UI, UX의 customerOrders</p>	
  </details>
</details>
<details>
  <summary><b>Supplier[정지연]</b></summary>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/supplier.jpg" />  
	<p> - customer와 동일한 UI, UX의 rawMaterialSupplier</p>	
</details>
<details>
  <summary><b>Factory(Machine)[전유범]<b></summary>
    <br>
  <details>
    <summary>Info</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_info.JPG" />
	  <p> - canvas 태그를 사용하여 공장에 설치되어있는 machine을 타일형태로 배치 GUI형태로 정보를 전달함</p>
	  <p> - Donut chart를 사용하여 목표생산량과 현재 생산량의 비율을 표시</p>
  </details>
  <details>
    <summary>Chart</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_chart.JPG" />
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_chart2.JPG" />
	  <p> - apexchart를 사용하여 공장에 들어오는 각 정보들을 시각화하여 표시함.</p>
	  <p> - 적절한 데이터 시각화를 위해 Line chart, Bar chart, Scatter chart, Donut chart, Heatmap chart등을 사용함.</p>
	  <p> - 필요한 자료들만을 화면상에 한번에 보이게 할 수 있도록 최소화 기능 구현</p>
  </details>
  <details>
    <summary>History</summary>
    <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/factory_history.JPG" />
	  <p> - 데이터의 규모가 커질 시 문제가 생기는 js 기반의 datatable 템플릿을 대체하기 위하여 일반 table을 사용하고 편의 검색, 정렬, 페이지네이션등의 기능을 따로 구현</p>
  </details>
</details>
<details>
  <summary>Factory Simulator[전유범]</summary>
  <img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/C%23Program.png" />
</details>

## 아키텍처
## ERD
<img src="https://github.com/JaehunKim-A/Final-Project/raw/main/finalProject_img/final2410_ERD.png" />
