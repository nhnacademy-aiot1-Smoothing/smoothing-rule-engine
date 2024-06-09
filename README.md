# 📖 룰 엔진
<br>
데이터를 받아 데이터를 정해진 규칙에 따라 처리합니다.
<br>
<br>
<div>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white">
<br>
<img src="https://img.shields.io/badge/OpenFeign-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br>
<img src="https://img.shields.io/badge/mqtt-660066?style=for-the-badge&logo=mqtt&logoColor=white">
<img src="https://img.shields.io/badge/rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white">
</div>
<br>

# 🛠️ 개발 내용
### 우혜승
- 디바이스 서버에서 Broker 추가 요청이 들어오면 해당하는 타입의 Broker를 생성
- 디바이스 서버에서 Topic 추가 요청이 들어오면 해당하는 Topic을 Broker에 Subscribe
- Broker 에러 발생시 MQ를 통해 디바이스 서버에 메시지를 전송
- 이상치 발생시 MQ를 통해 디바이스 서버에 메시지를 전송
### 신민석
- Rule Engine 구조설계
- Json Parsing 노드 구현
- Switch 노드 구현
