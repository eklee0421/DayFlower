
# Day flower (하루 한 송이)
공공데이터([농촌진흥청 국립원예특작과학원_오늘의 꽃 조회 서비스(2.0)](https://www.data.go.kr/iim/api/selectAPIAcountView.do "공공데이터 포탈로 이동"))를 활용하여 오늘의 꽃을 조회하는 안드로이드 어플리케이션입니다. 

# 패키지명
com.nyangzzi.dayFlower

# Build Version
+ kotlin 1.9.0
+ min sdk 28 
+ target sdk 33
+ jvm 17

# Quick Start

### install
  ```
  git clone https://github.com/nyangzzi/DayFlower.git
  ```

### set local.properties
**작성 예시** naver_client_id="AbCDefG1234567" <br/>
|키|값|포털|비고|
|:---|:---|:---|:---|
|search_flower_api_key|일반 인증키(Decoding)|[공공데이터 포털](https://www.data.go.kr/iim/api/selectAPIAcountView.do)|**농촌진흥청 국립원예특작과학원_오늘의 꽃 조회 서비스(2.0)** API 서비스 사용 신청 후 발급받은 키 사용|
|naver_client_id|Client ID|[Naver Developers](https://developers.naver.com/apps/#/myapps/WwO1gYPUeqIDcDglx2yX/overview)|**네이버 개발** dayflower 안드로이드 어플리케이션 패키지 등록 후 발급받은 키 사용|
|naver_client_secret|Client Secret|[Naver Developers](https://developers.naver.com/apps/#/myapps/WwO1gYPUeqIDcDglx2yX/overview)|**네이버 개발** dayflower 안드로이드 어플리케이션 패키지 등록 후 발급받은 키 사용|
|kakao_api_key|네이티브 앱 키|[kakao developer](https://developers.kakao.com/console/app/1016039/config/appKey)|**카카오 개발** dayflower 안드로이드 어플리케이션 패키지 등록 후 발급받은 키 사용|
|kakao_api_key_string|REST API 키|[kakao developer](https://developers.kakao.com/console/app/1016039/config/appKey)|**카카오 개발** dayflower 안드로이드 어플리케이션 패키지 등록 후 발급받은 키 사용|

### set google-services.json
&emsp;1. Firebase 콘솔로 이동 <br/>
&emsp;&emsp;▮ Firebase Console에 로그인 > 새 프로젝트 생성/프로젝트 선택 
<br/><br/>
&emsp;2. google-services.json 다운로드 <br/>
&emsp;&emsp;▮ 프로젝트 설정으로 이동 > "앱 설정"에서 "프로젝트 설정"으로 이동 > "일반" 탭에서 "Android 앱" 등록 >google-services.json 파일 다운
<br/><br/>
&emsp;3. 프로젝트에 google-services.json 추가 <br/>
&emsp;&emsp;▮ google-services.json 파일 app 폴더에 추가 (파일 경로 dayFlower/app/google-services.json)

<br/>



# 전체 기능

<br/>

▮ SNS 연동 로그인 (네이버/카카오)
<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/19ecebc4-2108-4575-bf66-0ac3aac6d576">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/6db3465a-718f-43c4-99a2-7d976e06ab1a">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/c6951741-5c5c-408c-95d7-1628beacd811">
</div>

<br/><br/>

▮ 홈 화면 / 꽃 상세 조회
<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/a40c5ab9-be70-4439-8be4-d705dbe37945">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/c0bc530f-ed77-4cd9-8cb8-f873df098a74">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/14e7ae49-f02d-47a8-9510-9756e0f48dcf">
</div>

<br/><br/>

▮ 캘린더 화면 / 스켈레톤 UI
<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/2ea19380-4006-494e-83da-e9add0149d9f">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/4e29af2f-baa1-444a-ab47-b0b67ba96b59">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/3e91ad77-292b-46b2-832b-2d63c730f483">
</div>

<br/><br/>

▮ 키워드 검색 전/후
<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/b9bcaffe-3fb3-407e-9478-fd5859eea63c">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/52eb8b37-24d9-4ff8-8521-85e09a18a153">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/1ea1fa00-5ae6-4b8d-9504-4ef4266a381a">
</div>

<br/><br/>

▮ 보관함 

<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/546ef774-2ab3-4e54-bd42-04a3069fe44e">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/604d106a-83b9-40ae-a3f4-15a2db028637">
  &nbsp;&nbsp;
 <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/1129cb1c-76f9-4394-8db7-2f12836bff24">
</div>

<br/><br/>

▮ 마이페이지 (개인 정보 수정)
<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/7e07d450-4c53-45ed-bdaa-58d8e612746d">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/b5d52cbb-07e0-4373-abb8-af0350a087b7">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/f747ee29-1341-4358-bbc9-44f1aff2aef3">
</div>

<br/><br/>

▮ 마이페이지 (앱정보/계정 설정)
<div>
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/3b8cff15-563f-4194-a8f7-d67b4d8ebdf6">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/113ac9e3-f6f6-4ca1-be7f-1e34bceaa2db">
  &nbsp;&nbsp;
  <img width="260" alt="image" src="https://github.com/nyangzzi/DayFlower/assets/52737339/1873d7be-eff8-48ae-8629-f1cedfd8aed0">
</div>
