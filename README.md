# 프로젝트 소개

JAVA / SPRING BOOT / JPA 기반 유저 관리 및 주문 관리 / 사용 API 구현

## 사용언어 및 프레임 워크

언어 : 
* Java 11 / Spring Boot 2.7.4

DB : 
* Mysql 8.0.31

기타 :
- 기본 포트 7000 , 내장 톰캣사용.
- Properties에 설정 된 Mysql 접속 정보
  - url=jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useLegacyDatetimeCode=false
  - username=test
  - password=test
- JDBC TIME ZONE 설정 : UTC
- 테스트 코드 ( Controller , Service ) 작성.
- 멤버 정보 암호화 ( 패스워드 - 단방향 ( SHA-256 ) , 그 외 정보 양방향 ( AES-256 ) )
- Swagger 설정.
- jar 내장 톰캣 실행.
- jwt 토큰 인증. ( SpringSecurity )

코드 구조 및 서비스 흐름.
 * 서비스에 회원 가입 및 로그인 가능.
 * 로그인 아이디 기반으로 단일유저 검색 / 주문 조회 API 호출 가능.
 * 로그인 시 JWT 토큰 발급 ( 주문 등록 API 시 사용 됨 )
 
---


# 서버 접속 및 실행 방법

## 1. 개인 구축 서버로 접속 방법

API 테스트 및 문서화 : https://kimyong.kr/backpac/swagger-ui/index.html#

## 2. Jar 파일로 서버 실행 방법. ( 스키마 IMPORT 필요 )

### Java - jdk 설치 ( 11 )
```
install java-11-openjdk-devel.x86_64
```

### git clone
```
git clone https://github.com/kimyooooong/order_api.git
```

### Sql Schema Import
```
( 프로젝트 내의 접속 정보는 DB : test , username : test , password : test 로 설정되어 있음. )
mysql -u [DB계정명] -p < [프로젝트폴더]/result/sql_schema.sql
```

### /result 폴더에 result.jar 실행
```
cd order_api/result
java -jar result.jar
```

## 3. Gradle build 후 서버 실행. ( 스키마 IMPORT 필요 )

### git clone
```
git clone https://github.com/kimyooooong/order_api.git
```

### Gradle Build ( JAR 파일 생성 )
```
cd order_api
./gradlew build
```

### Sql Schema Import
```
( 프로젝트 내의 접속 정보는 DB : test , username : test , password : test 로 설정되어 있음. )
mysql -u [DB계정명] -p < [프로젝트폴더]/result/sql_schema.sql
```

### JAR 실행
```
cd order_api/build/libs
java -jar order_api-1.0.jar
```
---

# 테스트 방법
```
1. 회원가입 ( POST ) - https://kimyong.kr/backpac/swagger-ui/index.html#/member-controller/joinUsingPOST
{
  "email": "stringn@ate.com",
  "genderKind": "FEMALE",
  "loginId": "string332",
  "name": "string",
  "nickname": "string",
  "password": "stringR123!!",
  "phoneNumber": "010-9981-8371"
} 를 통해 회원 가입 가능.

2. 로그인 ( POST ) - https://kimyong.kr/backpac/swagger-ui/index.html#/member-controller/loginUsingPOST
{
  "loginId": "string332",
  "password": "stringR123!!"
}
후 결과 값으로 토큰을 받아야함. 

결과 값 : 
{
  "success": true,
  "data": {
    "memberSeq": 1,
    "loginId": "string332",
    "name": "string",
    "nickName": "string",
    "phoneNumber": "010-9981-8371",
    "email": "stringn@ate.com",
    "gender": "FEMALE",
    "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmczMzIiLCJyb2xlcyI6W10sImlhdCI6MTY2NTkzNTEyMCwiZXhwIjoxNjY1OTM2OTIwfQ.e-wMYkVsaSGbbNa75fI7-tKYxhWUOOCoZfFqglBzpOs",
    "orderList": [],
    "createDate": "2022-10-17T00:45:04.678026"
  }
}

- jtwToken 값으로  주문 등록 API 사용가능. 
3. 주문 등록 API ( POST ) - https://kimyong.kr/backpac/swagger-ui/index.html#/orders-controller/addUsingPOST

4. 그 외 단일 회원 상세 정보 조회 , 단일 회원 주문 목록 조회 API 는 loginId Param 으로 사용 가능.
 - 단일 회원 상세 조회 ( GET ) - https://kimyong.kr/backpac/swagger-ui/index.html#/member-controller/detailUsingGET 
 - 단일 회원 주문 목록 조회 ( GET ) - https://kimyong.kr/backpac/swagger-ui/index.html#/member-controller/ordersUsingGET

5. 여러 회원 조회 ( GET ) - 페이지 네이션 , 검색 ( 이름 , 이메일 ) , 멤버의 마지막 주문정보포함 ( 정렬 : 최신순 ) 


```

