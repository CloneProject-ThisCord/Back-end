# ThisCord(Discord clone coding)
Websocket과 redis를 활용한 채팅기능을 구현 해보고자 Discord 클론코딩을 진행하였습니다.

## 📆개발기간
2022년 12월23일 ~ 2022년 12월 29일

## 👯팀원
**김소라**
<p>
  
[<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white">](https://github.com/dev-rara)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
</p>

**이재용**
<p>
  
[<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white">](https://github.com/yongYong0225)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
</p>  

**유상륜**  
<p>
  
[<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white">](https://github.com/YOOsangryun)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
</p>

## 🛠️ 기술 스택
* Spring Boot
* Spring Security + JWT
* Websocket
* Redis
* Amazon RDS(MySQL)
* Amazon EC2
  
## 💡트러블 슈팅
<details>
<summary><b>1. 서버 배포시 embeddedRedisConfig의 bean을 생성할 수 없없던 문제<b></summary>
<br>
<div markdown="1">
로컬환경에서는 embeddedRedis를 사용하여 application을 실행했는데, 설정해두었던 포트 번호(6379)를 변경해주지 않아서 서버 배포시 포트 충돌이 일어나 bean이 생성이 되지 않는 것이었다.<br>그래서 로컬에서 사용하는 포트 번호를 다르게 설정하고 무사히 배포를 진행할 수 있었다.
</div>
</details>

<details>
<summary><b>2. 서버 배포시 dto classNotFoundException이 발생했던 문제<b></summary>
<br>
<div markdown="2">  
  베이스 코드를 깃허브에 커밋했을 때 dto 패키지명을 Dto로 잘못 설정해두었던 걸, 개발 도중 알게되어 패키지 명을 변경했다.<br>
   그런데 변경된 패키지 명이 아닌 변경전 패키지 명이 깃허브에 그대로 남아있어, 서버 배포시 dto를 사용하는 클래스에서 import가 되지 않아 배포시에 classNotFoundException이 발생했다.<br>
   그래서 일단 dto 패키지명을 깃허브와 일치 시키고, import문을 수정하니 문제없이 배포를 진행할 수 있었다.<br>
   왜 깃허브에 커밋할 때는 충돌이 없었는지 찾아보니 MacOS에서는 대소문자 변경을 무시한다고 한다.<br>불행중 다행인지 백엔드 멤버 3명이 전부 MacOS 사용자 였기에 서버 배포시까지 충돌이 없었다. MacOS에서는 종종 발생하는 에러라고 하니 아래와 같은 명령어를 통해 대소문자 변경사항을 인식할 수 있도록 해주는 것이 좋을 것 같다.
   
   ```java
   //깃에서 케이스(대소문자) 변경사항을 무시하지 않도록 명령어를 통해 설정할 수 있다.
   git config core.ignorecase false
   ```  
   
</div>
</details>

<details>
<summary><b>3. websocket 연결 후, subscribe 과정에서 jsonwebtoken DecodingException이 발생했던 문제<b></summary>
<br>
<div markdown="3">
웹소켓을 통한 connetion이 성공하고 프론트에서 채팅방 구독을 요청하면 Decoding Exception이 발생했다.<br>
   token decoding 과정에서 공백이 들어간다는 에러 메세지 였는데, System.out.println()과 log를 이용해 확인한 결과 프론트에서는 토큰값을 잘 받아오고 있었다.<br> 또, jwt secret key의 값을 변경해보기도 했는데, 변함없이 같은 Exception이 발생했다. <br>해당 Exception은 아직 미해결로, 지속적으로 코드를 뜯어보고 수정해 나갈 것이다.
</div>
</details>

<details>
<summary><b>4. 인스턴스 서버 REDIS 설정시 발생했던 문제<b></summary>
<br>
<div markdown="3">REDIS를 이용한 서버배포를 위해 EC2서버에 REDIS 설치후 배포하였으나 문제발생<br>
   REDIS설정 시 인스턴스 서버 내에 외부접속을 위한 포트번호 미변경과 MEMORY설정 문제로 확인 외부접속을 위해 포트 넘버를 0.0.0.0으로 바꾸고 max memory설정을 통해 문제를 해결 
</div>
</details>

