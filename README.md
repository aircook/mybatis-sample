# MyBatis Sample Project

## 📋 프로젝트 개요

MyBatis와 Spring Boot를 학습하기 위한 샘플 프로젝트입니다. MyBatis의 다양한 기능(배치 처리, 인터셉터, 캐싱) 및 Redis를 활용한 고급 데이터 처리 기법을 다룹니다.

## 🎯 기본 정보

| 항목 | 값 |
|------|-----|
| 프로젝트명 | mybatis |
| 그룹 | com.tistory.aircook |
| 버전 | 0.0.1-SNAPSHOT |
| Java 버전 | 17 |
| 빌드 도구 | Gradle |

## 🏗️ 기술 스택

### 핵심 프레임워크
- **Spring Boot**: 3.2.0
- **MyBatis**: 3.0.3
- **Database**: SQLite (JDBC)

### 주요 의존성
- **캐싱**: Spring Cache, Redis
- **유틸리티**: Lombok, Guava, Apache Commons Lang3
- **개발**: Spring Boot DevTools, Embedded Redis
- **테스트**: JUnit 5, MyBatis Spring Boot Test

## 📁 프로젝트 구조

```
mybatis-sample/
├── src/
│   ├── main/
│   │   ├── java/com/tistory/aircook/mybatis/
│   │   │   ├── MybatisApplication.java          # 메인 애플리케이션 클래스
│   │   │   ├── controller/                      # REST 컨트롤러
│   │   │   │   ├── PeopleController.java
│   │   │   │   └── MemoController.java
│   │   │   ├── service/                         # 비즈니스 로직
│   │   │   │   ├── PeopleService.java
│   │   │   │   └── MemoService.java
│   │   │   ├── repository/                      # MyBatis Mapper Interface
│   │   │   │   ├── PeopleSimpleRepository.java
│   │   │   │   ├── PeopleBatchRepository.java
│   │   │   │   └── MemoRepository.java
│   │   │   ├── domain/                          # DTO / 도메인 객체
│   │   │   │   ├── PeopleRequest.java
│   │   │   │   ├── PeopleResponse.java
│   │   │   │   ├── MemoRequest.java
│   │   │   │   └── MemoResponse.java
│   │   │   └── config/                          # 설정 클래스
│   │   │       ├── database/
│   │   │       │   ├── MybatisSimpleConfig.java
│   │   │       │   ├── MybatisBatchConfig.java
│   │   │       │   ├── interceptor/
│   │   │       │   │   └── MybatisInterceptor.java
│   │   │       │   └── mapper/
│   │   │       │       └── BatchMapper.java
│   │   │       ├── data/
│   │   │       │   ├── RedisConfig.java
│   │   │       │   └── EmbeddedRedisConfig.java
│   │   │       └── cache/
│   │   │           ├── CacheConfig.java
│   │   │           └── CacheKeyEnum.java
│   │   └── resources/
│   │       ├── application.yml                  # 애플리케이션 설정
│   │       ├── schema.sql                       # 데이터베이스 스키마
│   │       ├── data.sql                         # 초기 데이터
│   │       └── com/tistory/aircook/mybatis/
│   │           └── repository/
│   │               ├── PeopleSimpleRepository.xml
│   │               ├── PeopleBatchRepository.xml
│   │               └── MemoRepository.xml        # MyBatis Mapper XML
│   └── test/
│       └── java/com/tistory/aircook/mybatis/
│           ├── MybatisApplicationTests.java
│           ├── controller/
│           │   └── MemoControllerTest.java
│           ├── service/
│           │   └── MemoServiceTest.java
│           └── repository/
│               ├── PeopleSimpleRepositoryTest.java
│               └── MemoRepositoryTest.java
├── gradle/                                      # Gradle 래퍼 파일
├── build.gradle                                 # Gradle 빌드 설정
├── settings.gradle                              # 멀티 프로젝트 설정
├── gradlew                                      # Gradle 래퍼 (Unix)
└── gradlew.bat                                  # Gradle 래퍼 (Windows)
```

## 🚀 주요 기능

### 1. People 관리
- **Simple Repository**: 단순 CRUD 작업
- **Batch Repository**: 대량 데이터 처리
- 두 개의 MyBatis 설정(SimpleConfig, BatchConfig)을 통한 분리

### 2. Memo 관리
- CRUD 기능 제공
- 서비스 레이어를 통한 비즈니스 로직 처리
- 컨트롤러를 통한 REST API 제공

### 3. 캐싱 전략
- **Redis 기반 캐시**: Spring Cache와 Redis 통합
- **Embedded Redis**: 개발 환경에서 자동 실행
- **CacheKeyEnum**: 캐시 키 관리

### 4. MyBatis 고급 기능
- **인터셉터**: MybatisInterceptor를 통한 쿼리 모니터링
- **Mapper XML**: SQL 쿼리 외부화
- **배치 처리**: 성능 최적화

## 📊 데이터베이스

### 데이터베이스: SQLite
- 자동 생성: `mydb.sqlite` (프로젝트 루트)
- 설정: `application.yml`의 `spring.datasource.url`

### 초기화 설정
- `schema.sql`: 테이블 스키마 생성
- `data.sql`: 초기 데이터 로드
- `spring.sql.init.mode: always`로 자동 실행

## 🔧 설정 상세

### MyBatis 설정 (`application.yml`)
```yaml
mybatis:
  configuration:
    map-underscore-to-camel-case: true  # 언더스코어 → 카멜케이스 매핑
```

### Redis 설정
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

### 데이터소스 설정
```yaml
spring:
  datasource:
    driver-class-name: org.sqlite.JDBC
    hikari:
      maximum-pool-size: 30
      pool-name: "[mydb-pool]"
```

### 로깅 설정
- Root: INFO
- Spring Cache/Transaction: TRACE
- MyBatis: DEBUG
- SQLite: DEBUG
- HikariCP: DEBUG
- 애플리케이션: DEBUG

## ⚠️ 주의사항

### MyBatis 버전 호환성
- Spring Boot 3.2.0 + MyBatis 3.0.2 조합 시 오류 발생:
  ```
  Invalid value type for attribute 'factoryBeanObjectType': java.lang.String
  ```
- **해결책**: MyBatis 3.0.3 이상 사용 (프로젝트에 적용됨)

### 보안 취약점
- `embedded-redis:0.7.3` 대신 `embedded-redis:1.2.0` 사용
  - 이유: commons-io 2.5 취약점 제거

## 🧪 테스트

### 테스트 실행
```bash
./gradlew test
```

### JVM 설정 (테스트)
- `-XX:+EnableDynamicAgentLoading`: 동적 에이전트 로딩 경고 방지
- `-Xshare:off`: 부트스트랩 클래스패스 공유 관련 경고 방지

### 테스트 클래스
- `MybatisApplicationTests`: 애플리케이션 컨텍스트 로드 테스트
- `PeopleSimpleRepositoryTest`: Simple Repository 테스트
- `MemoRepositoryTest`: Memo Repository 테스트
- `MemoServiceTest`: Service 로직 테스트
- `MemoControllerTest`: REST API 테스트

## 🏃 실행 방법

### 1. 프로젝트 빌드
```bash
./gradlew clean build
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. 테스트 실행
```bash
./gradlew test
```

## 📚 학습 포인트

이 프로젝트를 통해 학습할 수 있는 내용:

1. **MyBatis 기본**: Mapper Interface, XML 설정, CRUD 작업
2. **배치 처리**: 대량 데이터 처리 성능 최적화
3. **Spring 통합**: Dependency Injection, 자동 설정
4. **캐싱 전략**: Redis를 활용한 캐시 구현
5. **인터셉터 패턴**: MyBatis 인터셉터를 통한 쿼리 모니터링
6. **테스트 작성**: 단위 테스트 및 통합 테스트
7. **REST API 설계**: 컨트롤러를 통한 API 개발

## 📝 라이선스

개인 학습용 프로젝트

---

*Last Updated: 2025-11-24*
