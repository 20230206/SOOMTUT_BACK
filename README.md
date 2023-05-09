# soomtut
![image](https://user-images.githubusercontent.com/79249838/222885484-b215ab57-3599-46a7-a5e5-15f825c202ea.png)

## 팀원
- 이신희 LEADER
- 정성윤 VICE LEADER
- 박상훈
- 이은섭
- 임동규

## 서비스 및 프로젝트 소개 
로컬 기반 소셜 취미 클래스 플랫폼으로, 누구나 자신만의 취미와 재능으로 클래스를 오픈해 운영할 수 있고, 
수강생 매칭을 통해 유저는 쉽고 빠르게 다양한 클래스를 즐길 수 있도록 돕는 서비스입니다. 

## 기획 의도 
2019년 전세계를 강타한 코로나로 사람들의 외출이 제한되면서 잉여 시간을 활용하기 위해 취미생활/자기계발에 대한 관심이 높아졌습니다.
대표적인 플랫폼으로 성장한 클래스 101은 2021년 전년도 대비 매출액 성장 폭이 무려 59%를 달성했습니다.
그러나 기존 온라인 플랫폼들은 한계점 또한 분명했습니다. 

1. 강의의 최신성
온라인 강의들은 업로드 이후 최신화가 제대로 이루어지지 않았습니다. 
2. 유저의 좁은 선택폭
정해진 커리큘럼 대로 여러개의 강의 영상임 묶음으로 판매되기 때문에, 유저가 다양한 클래스를 참여하는 것이 어렵습니다.
3. 눈높이 교육 
불특정 다수를 상대로 하는 온라인 클래스는 각 유저의 개인 학습 속도와 배경지식을 고려하지 않아 배움의 어려움이 있습니다. 

'신희경지'팀은 이러한 기존 취미 플랫폼들의 이용 후기들을 살펴보며, 코로나가 잠식되어가며 사람들의 외부활동이 
많아지는 지금, 오프라인 클래스 플랫폼의 필요성을 느끼게 되었습니다. 앞선 문제들을 해결하며 저희는 지역 기반 튜터 매칭 서비스
'숨튜'를 기획하였습니다. 

1. 지역 기반으로 튜터를 매칭하여 대면 강의를 듣기 위해 소모되는 시간과 비용을 줄일 수 있도록 하였습니다. 
유저는 언제든 지정한 지역을 기준으로 근방에 다양한 클래스를 조회할 수 있습니다. 

2. 묶음이 아닌 원데이 클래스를 기준으로 하여 유저가 다양한 튜터들의 다양한 수업을 경험할 수 있도록 선택의 폭을 넓히고 시작 허들을 낮췄습니다.

## 사용 기술
 <h3>프론트엔드</h3>
  <ul>
    <li>React</li>
    <li>React Router</li>
    <li>Node.js</li>
    <li>Nginx</li>
  </ul>

  <h3>백엔드</h3>
  <ul>
    <li>Spring Boot</li>
    <li>Embedded Tomcat</li>
    <li>Gradle</li>
    <li>JDK 17</li>
  </ul>

  <h3>서버 간의 소통</h3>
  <ul>
    <li>Axios</li>
    <li>WebSocket</li>
    <li>Stomp</li>
  </ul>

  <h3>데이터베이스</h3>
  <ul>
    <li>MySQL</li>
  </ul>

  <h3>클라우드 플랫폼</h3>
  <ul>
    <li>Amazon RDS (Relational Database Service)</li>
    <li>Amazon EC2 (Elastic Compute Cloud)</li>
    <li>Amazon S3 (Simple Storage Service)</li>
  </ul>

  <h3>배포</h3>
  <ul>
    <li>Git Actions</li>
    <li>AWS CodeDeploy</li>
  </ul>
  
## 설치 및 실행 방법

### 프론트
1. 숨튜 프론트 프로젝트 클론
```
git clone https://github.com/20230206/SOOMTUT_FRONT.git
```
2. 프로젝트 디렉토리 이동
```
cd SOOMTUT_FRONT
```
3. 라이브러리 설치 
```
npm install
```
4. 프론트 개발 서버 실행
```
npm start
``` 

### 백엔드
1. 숨튜 백엔드 프로젝트 클론
```
git clone https://github.com/20230206/SOOMTUT_BACK.git
```
2. 프로젝트 디렉토리 이동
```
cd SOOMTUT_BACK
```
3. 라이브러리 설치 
```
./gradlew build
```
4. 프론트 개발 서버 실행
```
java -jar build/libs/soomtut-0.0.1-SNAPSHOT.jar
``` 
## 프로젝트 목표 

## 서비스 아키텍처
![image](https://user-images.githubusercontent.com/79249838/222732859-05e28a84-8bb8-4ecf-9162-e5482a3cf939.png)

##  Git-Flow 브랜치 전략
![03 (2) (1)](https://user-images.githubusercontent.com/79249838/222735404-a1055f35-eba8-457e-b5f4-6041af4bf3bb.svg)

## 화면 구성도
## ERD 구조
![image](https://user-images.githubusercontent.com/79249838/223445731-1ca05cac-9be8-449a-b04c-e0a2114003c2.png)

