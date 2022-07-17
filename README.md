# 과제

## 작성자
- Josh(Junseok Seo)

## 앱 구동 화면

|앱 초기화면|굿즈 노출 화면|수평 굿즈 노출 화면|스타일 굿즈 노출 화면|
|--------|-----------|---------------|----------------|
|![](https://user-images.githubusercontent.com/83396157/179382253-0ff3a309-733e-470b-8e4b-f40e6a6f1543.png)|![](https://user-images.githubusercontent.com/83396157/179382276-22d331d7-5194-4f50-ab98-477b45c2b446.png)|![](https://user-images.githubusercontent.com/83396157/179382278-6a084cb6-7a42-4063-8b85-b49cf4753eb6.png)|![](https://user-images.githubusercontent.com/83396157/179382279-123f5e4a-87b0-4bc0-bc83-4b0574630ace.png)|

## 추가과제 구현

### 더 보기 누를시
![mu_진짜 최종](https://user-images.githubusercontent.com/83396157/179382431-c2d4f4b6-c3c6-48c8-aa69-60cf230fd6c7.gif)
### 새로고침 누를시
새로고침을 누를시는 실제 무신사앱을 참고하여 작성하였으며, 완전한 랜덤이 아닌 처음 보여준 화면의 데이터 다음 위치에 존재하는 데이터들을 가져오는식으로
구성하였습니다. 이렇게 해야 모든 상품들을 고객에게 노출할 수 있을것이라 생각했기 때문입니다.
![mu_random](https://user-images.githubusercontent.com/83396157/179382447-b1da415a-5b2d-4604-bb81-50a5554cf0f9.gif)

## 사용한 기술
- 네트워크 연결
  - OkHttp3
  - Retrofit2

- JetPack
  - 데이터 바인딩
  - 뷰모델
  - 라이프사이클
- DI
  - Hilt
- 비동기 프로그래밍
  - Coroutine
- 앱 브라우저
  - CustomChromeTab
- 옵저버 패턴
  - 라이브데이터 
## Architecture - MVVM
![Image](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png?hl=ko)

## 기술적 도전

### 리사이클러뷰를 움직이지 않고 늘리게 만들기
- 기존에 리사이클러뷰를 사용할때 스크롤이 되는 부분을 막고 이를 리스트처럼 사용기 위해서 많은 시도를 해보았습니다.

### 하나의 어뎁터로 모든 리사이클러뷰를 그리기
- 뷰페이져, Horizontal RecyclerView, Grid RecyclerView 등 여러가지 리사이클러뷰를 하나의 어뎁터로 만드는 경우에 많은 어려움이 있었습니다.
- (미해결) Horizontal RecyclerView를 하나의 어뎁터로 그리지는 못하였습니다. (헤더와 리사이클러 뷰로 나눔, 다른 리사이클러뷰는
헤더 및 푸터까지 하나의 어뎁터에서 처리)

### 프로젝트내 모델 구성에 대한 고민
- DTO 에서 모델로 변환시에 모델을 어떻게 구성할지, 어떤식으로 DTO 에서 모델로 변경할지에 대한 고민을 많이 하게 되었습니다.
