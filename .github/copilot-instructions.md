# MyBatis Sample - AI 에이전트 지침

## 프로젝트 개요

**Spring Boot 3.2.0 + MyBatis 3.0.3 학습 프로젝트**로, 배치 처리, 캐싱 전략, 쿼리 인터셉션 등 고급 데이터 영속성 패턴을 시연합니다. SQLite를 영속성 저장소로 사용하고 Redis를 분산 캐싱에 사용하며 개발 환경에서는 임베디드 Redis를 포함합니다.

## 아키텍처 및 핵심 컴포넌트

### 3계층 아키텍처
- **컨트롤러 계층** (`src/main/java/.../controller/`): `ResponseEntity<T>`를 반환하는 REST 엔드포인트
- **서비스 계층** (`src/main/java/.../service/`): `@Service`, `@CacheConfig` 어노테이션을 사용한 비즈니스 로직
- **리포지토리 계층** (`src/main/java/.../repository/`): `src/main/resources/.../repository/*.xml`에 있는 XML 매퍼와 함께하는 MyBatis Mapper 인터페이스

### 이중 MyBatis 구성 (중요한 패턴)
프로젝트는 사용 사례에 따라 **두 개의 별도 MyBatis SqlSessionFactory** 구성을 사용합니다:

1. **`MybatisSimpleConfig`** (`config/database/MybatisSimpleConfig.java`)
   - `@Mapper`로 표시된 리포지토리
   - 기본 `ExecutorType.SIMPLE` (실행 시마다 하나의 명령문)
   - 쿼리 로깅을 위한 `MybatisInterceptor` 포함
   - 사용 대상: `PeopleSimpleRepository`, `MemoRepository`, `CompanyRepository`

2. **`MybatisBatchConfig`** (`config/database/MybatisBatchConfig.java`)
   - `@BatchMapper` (사용자 정의 어노테이션)로 표시된 리포지토리
   - 고성능 대량 삽입을 위해 `ExecutorType.BATCH` 사용
   - 별도의 `batchSqlSessionTemplate`과 `batchTransactionManager`
   - 사용 대상: `PeopleBatchRepository`

**핵심 포인트**: 리포지토리를 추가할 때 `@Mapper` 또는 `@BatchMapper`로 올바르게 표시하세요. 잘못된 어노테이션 라우팅은 매퍼 로드 실패를 초래합니다.

### 데이터 흐름 예시: Memo 작업
```
MemoController (/memos)
  ↓
MemoService (@CacheConfig 포함)
  ├─ @Cacheable (읽기용, CacheKeyEnum.MEMO_ALL 사용)
  └─ @CacheEvict/@Caching (쓰기용)
  ↓
MemoRepository (@Mapper)
  ↓
MemoRepository.xml (SELECT/INSERT/UPDATE/DELETE)
  ↓
SQLite (mydb.sqlite)
```

## 중요한 규칙 및 패턴

### 1. Mapper 어노테이션 라우팅
- **`@Mapper`** → `MybatisSimpleConfig`로 라우팅 → `sqlSessionTemplate` 사용
- **`@BatchMapper`** → `MybatisBatchConfig`로 라우팅 → `batchSqlSessionTemplate` 사용
- 어노테이션을 혼합하면 조용히 실패하거나 잘못된 세션 팩토리를 사용합니다

### 2. 캐시 키 관리
- 모든 캐시 키에 문자열 리터럴이 아닌 `CacheKeyEnum` (`config/cache/`에 위치)을 사용하세요
- 예시: `@Cacheable(key = "T(com.tistory.aircook.mybatis.config.cache.CacheKeyEnum).MEMO_ALL")`
- 이렇게 하면 캐시 어노테이션에서 SpEL 열거형 해석을 사용할 수 있습니다

### 3. 엔티티 응답 전략
- 도메인 객체는 Redis 캐싱을 위해 `Serializable` 구현 (`MemoResponse` 참고)
- Lombok의 `@Data @Builder`를 DTO/Response 객체에 사용하세요
- Request/Response 클래스 분리 (영속성 계층과 공유하지 않음)

### 4. MyBatis XML 매퍼 패턴
- 위치: `src/main/resources/com/tistory/aircook/mybatis/repository/`
- 네임스페이스는 인터페이스 FQN과 일치: `com.tistory.aircook.mybatis.repository.MemoRepository`
- FQN 또는 별칭으로 `parameterType`과 `resultType` 사용 (`map-underscore-to-camel-case: true`를 통해 구성)

### 5. 테스트 규칙
- **단위 테스트**: `@Mock`과 `@InjectMocks`와 함께 `@ExtendWith(MockitoExtension.class)` 사용
- **통합 테스트**: `@SpringBootTest`와 `@DataJpaTest` 또는 임베디드 테스트 컨테이너 사용
- **테스트용 JVM 인자**: 
  ```gradle
  jvmArgs = ['-XX:+EnableDynamicAgentLoading', '-Xshare:off']
  ```

## 빌드, 테스트 및 실행 명령어

```bash
# 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun

# 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests MemoControllerTest
```

## 중요한 구성 상세

### MyBatis 구성 (`application.yml`)
```yaml
mybatis:
  configuration:
    map-underscore-to-camel-case: true  # DB_COLUMN → dbColumn
    call-setters-on-nulls: true
    jdbc-type-for-null: NULL
```

### 데이터베이스
- **유형**: SQLite
- **위치**: `mydb.sqlite` (프로젝트 루트에 자동 생성)
- **초기화**: `spring.sql.init.mode: always`를 통해 `schema.sql`과 `data.sql`이 자동 실행

### 캐싱
- **주요**: Redis (`spring-boot-starter-data-redis`를 통해)
- **개발**: 임베디드 Redis 자동 시작 (`embedded-redis:1.2.0`을 통해)
- **주의**: Spring Data Redis를 사용할 때 기본 `CacheManager`는 `ConcurrentMapCache`에서 `RedisCache`로 전환됩니다

### 로깅 수준
`application.yml`에서 설정:
- `org.springframework.cache: trace` - 캐시 히트/미스 가시성
- `com.tistory.aircook.mybatis: debug` - 애플리케이션 SQL 로깅
- `org.mybatis: debug` - MyBatis 내부 동작

## 주의할 점

### MyBatis 버전 호환성
- Spring Boot 3.2.0 + MyBatis 3.0.2 원인: `Invalid value type for attribute 'factoryBeanObjectType': java.lang.String`
- **해결책**: MyBatis 3.0.3 이상 사용 (이미 `build.gradle`에 있음)

### 보안/의존성 문제
- `embedded-redis:0.7.3` 회피 (commons-io 2.5 취약점 포함)
- 프로젝트는 포크된 `embedded-redis:1.2.0` 사용 - 이를 유지하세요

### 직렬화
- Redis 캐싱을 위해 모든 캐시된 객체는 `Serializable`을 구현해야 함
- `MemoResponse`가 명시적으로 구현 - 새로운 캐시된 도메인 객체에 대해 이 패턴을 따르세요

## 파일 위치 빠른 참조

| 목적 | 경로 |
|------|------|
| Mapper 인터페이스 | `src/main/java/.../repository/*.java` |
| Mapper XML | `src/main/resources/.../repository/*.xml` |
| 서비스 (캐시 로직) | `src/main/java/.../service/` |
| 캐시 키 | `config/cache/CacheKeyEnum.java` |
| MyBatis 구성 | `config/database/Mybatis*Config.java` |
| MyBatis 인터셉터 | `config/database/interceptor/MybatisInterceptor.java` |
| 엔티티/DTO | `src/main/java/.../domain/` |
| REST 컨트롤러 | `src/main/java/.../controller/` |

## 새로운 기능 추가

### 새로운 캐시된 엔티티 추가
1. `domain/`에서 Request/Response DTO 생성 (Response를 `@Serializable`로 표시)
2. `@Mapper`가 있는 `repository/`에 리포지토리 인터페이스 생성
3. `src/main/resources/.../repository/`에서 해당 XML 매퍼 생성
4. `CacheKeyEnum`에 캐시 키 추가
5. `CacheKeyEnum`을 사용하는 캐시 어노테이션을 포함한 서비스 생성
6. `@RestController`와 `@RequestMapping`을 포함한 컨트롤러 추가
7. `MockitoExtension`으로 단위 테스트 및 통합 테스트 추가

### 배치 작업 추가
1. `@BatchMapper` 어노테이션이 있는 `YourBatchRepository` 인터페이스 생성
2. 해당 XML 매퍼에서 메서드 정의 (네임스페이스는 인터페이스 FQN과 일치해야 함)
3. `YourBatchRepository` 주입 (`PeopleBatchRepository` 템플릿이 아님)
4. `@Transactional` 경계 내에서 메서드 호출 (배치 실행자에 중요)

## 디버깅 팁

- **캐시가 작동하지 않음**: `CacheKeyEnum` 사용 확인 - SpEL `T()` 구문 반드시 사용
- **매퍼를 찾을 수 없음**: 어노테이션이 구성과 일치하는지 확인 (@Mapper vs @BatchMapper)
- **SQL이 로깅되지 않음**: `application.yml`에서 `com.tistory.aircook.mybatis`가 `debug` 수준에 있는지 확인
- **배치 삽입이 느림**: `@BatchMapper` 사용 및 `@Transactional` 경계 내에 있는지 확인
- **Redis가 연결되지 않음**: 임베디드 Redis가 시작되었는지 확인 - 로그에 포트 6379가 표시되어야 함
