# 캡스톤 디자인 (back)

---

## todo 웹앱 Spring Boot 백엔드 프로젝트

### 🔧 개발도구
* IntelliJ

### ㏈ 데이터베이스
* h2Database

### 📚 dependency(의존성)
```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
implementation 'org.springframework.boot:spring-boot-starter-web'
compileOnly 'org.projectlombok:lombok'
runtimeOnly 'com.h2database:h2'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
implementation 'org.mapstruct:mapstruct:1.4.2.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
```
### 📂 디렉터리 구조
* Controller : RestAPI
* Dto : 객체
* Entity : 데이터베이스 객체
* Mapper : mapsturct 이용한 dto←→entity(변환)
* Repository : database 쿼리
* Response : http 상태코드, 데이터 반환 로직
* Service : CRUD 기능 구현


### 📄 API 명세서
[노션링크](https://receptive-coach-a3f.notion.site/b6aba3230c1444feb98740871393ddda?v=a855958aee49469c9279fb0e67f2eb44&pvs=4)

---

# 현재 상황

### 👥 회원
1. 이메일 유효성 검사, 회원가입 기능 구현 ✅
2. 로그인 기능 구현 ✅
3. 회원 정보 수정 ☑️
4. 회원 삭제 ☑️

### 📋 Todo
1. todo 생성 ✅
2. todo 수정 ✅
3. todo 삭제 ✅

### 👍 좋아요(찜 기능)☑️

### 🧭 길찾기(확정 X)

