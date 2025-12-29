# Company API 테스트 계획

## 📋 테스트 목표
생성된 Company API의 모든 기능(CRUD)이 정상적으로 동작하는지 검증합니다.

## 🎯 테스트 범위

### 1. 단위 테스트 (Unit Test)
- **CompanyControllerTest**: REST API 엔드포인트 테스트
- **CompanyServiceTest**: 비즈니스 로직 테스트

### 2. 통합 테스트 (Integration Test)
- **CompanyRepositoryTest**: MyBatis Mapper와 실제 DB 연동 테스트

### 3. 수동 API 테스트
- Postman/Insomnia를 이용한 실제 HTTP 요청 테스트
- curl 명령어를 이용한 테스트

---

## 📝 테스트 케이스 상세

### 1. CompanyControllerTest (단위 테스트)

#### 테스트 환경
- **프레임워크**: JUnit 5, Mockito, MockMvc
- **방식**: Service 레이어를 Mock으로 처리하여 Controller만 테스트

#### 테스트 케이스

##### 1.1 전체 회사 목록 조회 (`GET /companies`)
- **목적**: 모든 회사 정보를 조회하는 API 테스트
- **검증 항목**:
  - HTTP Status 200 OK
  - 응답 본문이 List 형태
  - Service 메서드 호출 여부 확인

##### 1.2 회사 단건 조회 (`GET /companies/{id}`)
- **목적**: 특정 ID의 회사 정보 조회
- **검증 항목**:
  - HTTP Status 200 OK
  - 응답 본문에 회사 정보 포함
  - Service 메서드 호출 여부 확인

##### 1.3 회사 생성 (`POST /companies`)
- **목적**: 새로운 회사 정보 등록
- **검증 항목**:
  - HTTP Status 201 CREATED
  - Request Body 검증
  - Service 메서드 호출 여부 확인

##### 1.4 회사 수정 (`PUT /companies`)
- **목적**: 기존 회사 정보 수정
- **검증 항목**:
  - HTTP Status 200 OK
  - Request Body 검증
  - Service 메서드 호출 여부 확인

##### 1.5 회사 삭제 (`DELETE /companies/{id}`)
- **목적**: 특정 회사 정보 삭제
- **검증 항목**:
  - HTTP Status 200 OK
  - Service 메서드 호출 여부 확인

---

### 2. CompanyServiceTest (단위 테스트)

#### 테스트 환경
- **프레임워크**: JUnit 5, Mockito
- **방식**: Repository 레이어를 Mock으로 처리하여 Service만 테스트

#### 테스트 케이스

##### 2.1 전체 회사 목록 조회
- **Given**: Mock Repository가 회사 목록 반환
- **When**: `findCompanyAll()` 호출
- **Then**: 반환된 목록의 크기 및 내용 검증

##### 2.2 회사 단건 조회
- **Given**: Mock Repository가 특정 회사 정보 반환
- **When**: `findCompanyById(id)` 호출
- **Then**: 반환된 회사 정보 검증

##### 2.3 회사 생성
- **Given**: CompanyRequest 객체 생성
- **When**: `insertCompany(companyRequest)` 호출
- **Then**: Repository의 `insertCompany()` 메서드 호출 확인

##### 2.4 회사 수정
- **Given**: CompanyRequest 객체 생성 (id 포함)
- **When**: `updateCompany(companyRequest)` 호출
- **Then**: Repository의 `updateCompany()` 메서드 호출 확인

##### 2.5 회사 삭제
- **Given**: 회사 ID
- **When**: `deleteCompanyById(id)` 호출
- **Then**: Repository의 `deleteCompanyById()` 메서드 호출 확인

---

### 3. CompanyRepositoryTest (통합 테스트)

#### 테스트 환경
- **프레임워크**: JUnit 5, MyBatis Test (`@MybatisTest`)
- **방식**: 실제 SQLite DB에 연결하여 MyBatis Mapper 동작 검증

#### 테스트 케이스

##### 3.1 전체 회사 목록 조회
- **Given**: `data.sql`에 초기 데이터 3건 존재
- **When**: `findCompanyAll()` 호출
- **Then**: 반환된 목록의 크기가 3 이상인지 확인

##### 3.2 회사 단건 조회
- **Given**: 새로운 회사 정보를 DB에 저장
- **When**: `findCompanyById(저장된 ID)` 호출
- **Then**: 저장한 정보와 조회한 정보가 일치하는지 확인

##### 3.3 회사 생성
- **Given**: CompanyRequest 객체 생성
- **When**: `insertCompany(companyRequest)` 호출
- **Then**: 
  - 반환된 ID가 null이 아닌지 확인
  - 저장된 데이터를 조회하여 입력값과 일치하는지 확인

##### 3.4 회사 수정
- **Given**: 기존 회사 정보 수정 요청
- **When**: `updateCompany(companyRequest)` 호출
- **Then**: 수정된 데이터를 조회하여 변경사항이 반영되었는지 확인

##### 3.5 회사 삭제
- **Given**: 기존 회사 ID
- **When**: `deleteCompanyById(id)` 호출
- **Then**: 삭제 후 조회 시 null 반환 확인

---

## 🛠️ 수동 API 테스트 방법

### 1. 애플리케이션 실행
```bash
./gradlew bootRun
# 또는
./gradlew.bat bootRun  # Windows
```

### 2. Postman/Insomnia를 이용한 테스트

#### 2.1 전체 회사 목록 조회
```
GET http://localhost:8080/companies
Content-Type: application/json
```

#### 2.2 회사 단건 조회
```
GET http://localhost:8080/companies/1
Content-Type: application/json
```

#### 2.3 회사 생성
```
POST http://localhost:8080/companies
Content-Type: application/json

{
  "name": "삼성전자",
  "industry": "전자제품 제조",
  "employeeCount": 267937,
  "foundedYear": 1969
}
```

#### 2.4 회사 수정
```
PUT http://localhost:8080/companies
Content-Type: application/json

{
  "id": 1,
  "name": "삼성전자(주)",
  "industry": "전자제품 제조",
  "employeeCount": 270000,
  "foundedYear": 1969
}
```

#### 2.5 회사 삭제
```
DELETE http://localhost:8080/companies/1
Content-Type: application/json
```

### 3. curl 명령어를 이용한 테스트

#### 3.1 전체 회사 목록 조회
```bash
curl -X GET http://localhost:8080/companies \
  -H "Content-Type: application/json"
```

#### 3.2 회사 단건 조회
```bash
curl -X GET http://localhost:8080/companies/1 \
  -H "Content-Type: application/json"
```

#### 3.3 회사 생성
```bash
curl -X POST http://localhost:8080/companies \
  -H "Content-Type: application/json" \
  -d '{
    "name": "네이버",
    "industry": "인터넷 서비스",
    "employeeCount": 3500,
    "foundedYear": 1999
  }'
```

#### 3.4 회사 수정
```bash
curl -X PUT http://localhost:8080/companies \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "네이버(주)",
    "industry": "인터넷 서비스",
    "employeeCount": 4000,
    "foundedYear": 1999
  }'
```

#### 3.5 회사 삭제
```bash
curl -X DELETE http://localhost:8080/companies/1 \
  -H "Content-Type: application/json"
```

---

## 📊 테스트 실행 순서

### 1단계: 단위 테스트 실행
```bash
./gradlew test --tests "com.tistory.aircook.mybatis.controller.CompanyControllerTest"
./gradlew test --tests "com.tistory.aircook.mybatis.service.CompanyServiceTest"
```

### 2단계: 통합 테스트 실행
```bash
./gradlew test --tests "com.tistory.aircook.mybatis.repository.CompanyRepositoryTest"
```

### 3단계: 전체 테스트 실행
```bash
./gradlew test
```

### 4단계: 수동 API 테스트
- 애플리케이션 실행 후 Postman/curl로 실제 API 호출 테스트

---

## ✅ 검증 체크리스트

### Controller 테스트
- [ ] GET /companies - 전체 목록 조회 성공
- [ ] GET /companies/{id} - 단건 조회 성공
- [ ] POST /companies - 회사 생성 성공 (201 CREATED)
- [ ] PUT /companies - 회사 수정 성공 (200 OK)
- [ ] DELETE /companies/{id} - 회사 삭제 성공 (200 OK)

### Service 테스트
- [ ] findCompanyAll() - 전체 목록 조회 로직 검증
- [ ] findCompanyById() - 단건 조회 로직 검증
- [ ] insertCompany() - 회사 생성 로직 검증
- [ ] updateCompany() - 회사 수정 로직 검증
- [ ] deleteCompanyById() - 회사 삭제 로직 검증

### Repository 테스트
- [ ] findCompanyAll() - DB에서 전체 목록 조회 성공
- [ ] findCompanyById() - DB에서 단건 조회 성공
- [ ] insertCompany() - DB에 회사 정보 저장 성공
- [ ] updateCompany() - DB에서 회사 정보 수정 성공
- [ ] deleteCompanyById() - DB에서 회사 정보 삭제 성공

### 수동 API 테스트
- [ ] Postman/curl로 모든 엔드포인트 정상 동작 확인
- [ ] 응답 JSON 형식 검증
- [ ] 에러 케이스 테스트 (존재하지 않는 ID 조회 등)

---

## 🐛 예상되는 이슈 및 해결 방법

### 1. 테스트 실행 시 DB 연결 오류
- **원인**: SQLite 파일 경로 문제
- **해결**: `application.yml`의 datasource 설정 확인

### 2. MyBatis Mapper XML 파일을 찾을 수 없음
- **원인**: XML 파일 경로 불일치
- **해결**: `src/main/resources/com/tistory/aircook/mybatis/repository/` 경로 확인

### 3. 초기 데이터가 없는 경우
- **원인**: `data.sql` 실행 실패
- **해결**: `schema.sql`과 `data.sql` 파일 확인

---

## 📚 참고 자료
- 기존 테스트 코드: `MemoControllerTest`, `MemoServiceTest`, `MemoRepositoryTest`
- MyBatis Test 문서: https://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/

