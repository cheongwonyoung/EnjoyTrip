# **1. Enjoy Trip 소개**

- Enjoy Trip은 사용자들에게 다양한 관광지 및 주변시설을 소개해주는 웹 사이트입니다.
- 사용자 위치 정보 기반 관광지 소개, 지역별 관광지 소개, 지역별 주변시설 조회 기능, 여행 계획 공유 게시판 기능을 가지고 있습니다.
- ER Diagram
  ![Untitled](https://user-images.githubusercontent.com/86768006/203838828-d7502014-86f9-4f96-8536-5760164f95ad.png)
- Class Diagram

![Untitled2](https://user-images.githubusercontent.com/86768006/203839402-50e9bce0-93b5-4400-9922-56bf2f7ed3f7.png)

- usecase

![Untitled3](https://user-images.githubusercontent.com/86768006/203839761-d8516048-68b3-48e5-a869-7f5f192ee285.png)

# **2. 기능**

### 1) 메인 페이지

![sst](https://user-images.githubusercontent.com/86768006/203840590-c0d84775-8ba5-4902-83de-1e23c6c7205b.png)

1. nav bar
   - 로그인, 회원가입, 일정 공유, 여행지 추천, 여행지 주변시설 페이지로 이동 가능
   - 로그인 상태일 때는 로그인, 회원가입 대신 로그아웃, 마이페이지를 출력
2. 사용자 위치 기반 주변 관광지 소개
   - 사용자 위치 정보(위도, 경도)를 이용해서 그 주변 이미지가 있는 관광지 정보를 가까운 거리순으로 4개를 출력

### 2) 메인 페이지에서 '고른햇살' 버튼을 눌렀을 때

해당 관광지의 정보 출력

- 주소, 상세 주소, 전화번호 출력
- 만약 존재하지 않을 경우 생략

### 3) 로그인 페이지

![Untitled5](https://user-images.githubusercontent.com/86768006/203839819-8a1fdbbe-9f8f-4382-87ea-eb5d214f032e.png)

1. 아이디 저장
   - 이전에 로그인한 정보(아이디)를 저장
2. 비밀번호 찾기
   - 비밀번호 찾기 모달창을 띄움
3. 회원가입 페이지로 연결되는 버튼

### 4) 비밀번호 찾기 모달창

![Untitled6](https://user-images.githubusercontent.com/86768006/203839837-b4bf5803-0602-44dc-9cb1-e946416b3baf.png)

1. 현재 비밀번호 출력
   - 올바른 아이디와 이름이 입력되면 현재 비밀번호를 출력
   - 틀린 아이디와 이름이 입력되면 "일치하는 계정이 없습니다."라고 띄움

### 5) 회원가입 페이지

![Untitled7](https://user-images.githubusercontent.com/86768006/203839844-9091bd41-1242-4d79-bfe3-c8970902505e.png)

1. 회원가입 폼
   - 폼에 입력된 정보에 따라 알맞은 alert창을 띄움
2. 회원가입 버튼

### 6) 마이 페이지

![Untitled8](https://user-images.githubusercontent.com/86768006/203839900-d375d75f-3ee4-4c43-b8a5-157695f15911.png)

![Untitled9](https://user-images.githubusercontent.com/86768006/203839901-fffbe997-b084-4f88-8248-488cb99fc846.png)

1. 회원정보 수정 페이지로 연결되는 버튼
2. 회원탈퇴 버튼
   - 회원탈퇴 모달창을 띄움
   - 올바른 비밀번호를 입력하면 회원탈퇴!

### 7) 프로필 변경 페이지

![Untitled10](https://user-images.githubusercontent.com/86768006/203839904-69a27546-7e81-4d4d-bf13-0f40e2d780f3.png)

1. 현재 사용자의 정보 출력
   - 바꾸고 싶은 정보 입력
   - 올바른 비밀번호를 입력해야 변경 가능
2. 비밀번호 보기 버튼
   - 기본적으로 비밀번호는 마스킹 처리
   - 버튼 클릭시 비밀번호 마스킹 처리가 사라지고 비밀번호
3. 프로필 설정 버튼
   - 원하는 이미지를 불러와 프로필 이미지로 설정
4. 정보 변경 완료 버튼
   - 버튼 클릭시 password란에 입력된 정보와 현재 비밀번호가 일치한지 검사
   - 비밀번호가 일치하면 마이페이지로 리다이렉트

### 8) 여행지 추천 페이지

![Untitled11](https://user-images.githubusercontent.com/86768006/203839907-c1e14ee9-1519-4e05-b6c8-dbba0475418e.png)

1. 로그인시 바뀐 navbar 모습
2. 지역 설정 select box
   - 시도선택을 하면 구군선택 option이 변경
   - 시도, 구군을 모두 선택하고 카테고리 버튼을 누르면 해당 지역의 관광지 18개의 대표 이미지 출력
   - 18개 선정 기준 : 이미지가 있는 관광지 정보를 조회순으로 정렬한 후 상위 18개
3. 카테고리 설정
   - 18개의 관광지를 카테고리별로 볼 수 있는 버튼
4. 관광지 대표 이미지 출력
   - 지역 설정을 하지 않았을 경우 : 사용자 위치 정보를 이용해서 그 주변 이미지가 있는 관광지 정보를 가까운 거리순으로 18개 출력
   - 관광지 대표 이미지를 클릭하면 관광지 상세정보 모달로 이동

### 9) 관광지 상세정보

![Untitled12](https://user-images.githubusercontent.com/86768006/203839874-24254fe0-d8ac-42d8-a293-eb93b71bc3f0.png)

1. 해당 관광지의 대표 이미지 출력
2. 해당 관광지의 정보 출력 (제목, 주소, 전화번호)

### 10) 여행지 주변시설 페이지

![Untitled13](https://user-images.githubusercontent.com/86768006/203839884-c54f7b42-8dea-4290-a5d7-8535ac260f38.png)

1. 사용자 위치를 마커로 표시

![Untitled14](https://user-images.githubusercontent.com/86768006/203839885-44592e69-efc8-4741-a3e1-62f1f2c3ff47.png)

1. 지역/옵션 설정 select box
   - 시도선택을 하면 구군선택 option이 변경
   - 구군선택을 하면 옵션선택 option이 변경
   - 모두 선택하고 Go! 버튼을 누르면 해당 지역의 해당 카테고리인 모든 시설의 위치를 맵에 마커로 표시
2. 카카오 맵 api
   - 마커들의 위치에 따라 지도 범위가 재설정
3. 마커를 누르면 볼 수 있는 custom overlay

![Untitled15](https://user-images.githubusercontent.com/86768006/203839889-093ae612-924b-470a-98fa-4c7731e59c38.png)

- 다른 마커를 클릭하면 이전 custom overlay는 사라짐

### 11) 일정 공유 목록 페이지

![Untitled16](https://user-images.githubusercontent.com/86768006/203839891-24d74e65-7c18-4491-b1f0-14156151bb09.png)

1. 일정 공유 작성 페이지로 이동하는 버튼
2. 공유된 일정을 간단하게 보여줌 (썸네일, 제목, 조회수)
   - 일정 공유 게시글을 누를 때 조회수 증가

### 12) 일정 공유 작성 페이지

![Untitled17](https://user-images.githubusercontent.com/86768006/203839893-39f88596-1c7e-484c-b740-28d60e8df11c.png)

![Untitled18](https://user-images.githubusercontent.com/86768006/203839894-ed8856dd-1d6f-4b05-b53c-83c32c2abc59.png)

1. 게시물 제목
2. 관광지 검색
   - 검색어를 입력하고 검색 버튼 클릭 (enter도 가능)
3. 카카오 맵 api
4. 검색된 관광지 결과를 리스트로 출력
   - 관광지를 클릭하면 카카오 맵 범위가 해당 관광지쪽으로 바뀜
   - 체크박스를 선택하면 해당 관광지가 관광지 카트에 들어감
5. 관광지 카트
   - 짧은 설명을 관광지마다 남길 수 있음
6. 등록 버튼

### 13) 일정 공유 상세 페이지

![Untitled19](https://user-images.githubusercontent.com/86768006/203839897-b7e6ad5e-b070-498d-9dab-97bb632599cd.png)

1. 목록보기, 삭제하기 버튼
   - 목록보기 버튼을 클릭하면 일정 공유 목록 페이지로 이동
   - 글을 작성한 회원만 삭제하기 버튼이 보임
