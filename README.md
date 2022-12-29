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
<p></p>
</div>
</details>

<details>
<summary><b>2. 서버 배포시 dto classNotFoundException이 발생했던 문제<b></summary>
<br>
<div markdown="2">  
<b>베이스 코드를 깃허브에 커밋했을 때 dto 패키지명을 Dto로 잘못 설정해두었던 걸, 개발 도중 알게되어 패키지 명을 변경했다.
   그러나 변경된 패키지 명이 아닌 변경전 패키지 명이 깃허브에 그대로 남아있어, 서버 배포시 dto를 사용하는 클래스에서 import가 되지 않아 배포시에 classNotFoundException이 발생했다.
   그래서 일단 dto 패키지명을 깃허브와 일치 시키고, import문을 수정하니 문제없이 배포를 진행할 수 있었다.
   왜 깃허브에 커밋할 때는 충돌이 없었는지 찾아보니 MacOS에서는 대소문자 변경을 무시한다고 한다. 백엔드 멤버 3명이 전부 MacOS 사용자 였기에 서버 배포시까지 충돌이 없었던 것 같다.</b>  
   
   ```java
   //깃에서 케이스(대소문자) 변경사항을 무시하지 않도록 명령어를 통해 설정할 수 있다.
   
   git config core.ignorecase false
   ```  
   
</div>
</details>

<details>
<summary><b>3. websocket 연결 후, subscribe 과정에서 Token DecodingException이 발생했던 문제<b></summary>
<br>
<div markdown="3">
<p></p>
</div>
</details>
