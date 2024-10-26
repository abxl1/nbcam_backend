### # SPRING PLUS - 주특기 플러스주차 개인과제

### # 프로젝트 개요
* 실전 프로젝트 이전 마지막 개인 프로젝트 진행 주차에서 그간 배웠던 기술 스택과 문법, 개념들을 복습하기 위함
* 필수 기능 구현 섹션에서는 JPA, JWT, QueryDSL, Spring Security를 적용한 코드와 이 구현에서 발생하는 N+1 문제, 성능 저하 등을 해결하도록 함
* 도전 기능 구현 섹션에서는 필수 구현 기능에서 확장하여 심화된 QueryDSL, AWS 대용량 데이터 처리 등의 기술을 구현함

### # API 명세 

- 회원 관리

| 기능 | method | URL | request header | request | response |
|-----|--------|-----|----------------|--------|--------|
| 회원가입 | POST | /auth/signup | - | requestBody | Bearer Token |
| 로그인 | POST | /auth/signin | - | requestBody | Bearer Token |
| 회원 조회 | GET | /users/{userId} | Authentication Token | PathVariable | UserResponse |
| 비밀번호 변경 | PUT | /users | Authentication Token | requestBody | HTTP CODE |
</br>

- 일정 관리

| 기능 | method | URL | request header | request | response |
|-----|--------|-----|----------------|--------|--------|
| 일정 생성 | POST | /todos | Authentication Token | requestBody | TodoSaveResponse |
| 일정 단건 조회 | GET | /todos/{todoId} | Authentication Token | PathVariable | TodoResponse |
| 일정 다건 조회 | GET | /todos | Authentication Token | requestParam | TodoResponse |
| 일정 다건 조회 - QueryDSL | GET | /todos/query | Authentication Token | requestBody | TodoProjectionsDto |
</br>

- 댓글 관리

| 기능 | method | URL | request header | request | response |
|-----|--------|-----|----------------|--------|--------|
| 댓글 생성 | POST | /todos/{todoId}/comments | Authentication Token | PathVariable, RequestBody | CommentSaveResponse |
| 댓글 조회 | GET | /todos/{todoId}/comments | Authentication Token | PathVariable | CommentResponse |
</br>

- 담당자 관리

| 기능 | method | URL | request header | request | response |
|-----|--------|-----|----------------|--------|--------|
| 담당자 생성 | POST | /todos/{todoId}/managers | Authentication Token | PathVariable, RequestBody | ManagerSaveResponse |
| 담당자 조회 | GET | /todos/{todoId}/managers | Authentication Token | PathVariable | ManagerResponse |
| 담당자 삭제 | DELETE | /todos/{todoId}/managers/{managerId} | Authentication Token | PathVariable | HTTP CODE |
</br>

### # 트러블 슈팅

* 1번 문제
1) QueryDSL 적용 시에 build > ... > domain > entity 패키지 내의 엔티티 클래스들의 Q클래스가 생성이 되지 않는 문제가 발생함
- Q클래스가 생성되지 않는 경우는 적지 않게 생김, build-gradle의 QueryDSL에 대한 의존 추가가 제대로 되었는지 확인하고 추가가 완료된 경우에도 생성되지 않는다면 build-clean, build-classes 등의 빌드 작업을 다시 시도하여 해결함.

2) 매니저 저장 요청 시 Log 테이블에 요청 시각과 그 외 추가적인 정보들을 저장할 때 AOP 코드를 사용하게 됨. 이 과정에서 요청한 사용자의 id도 저장하기 위해 joinPoint에서 userId를 가져오려고 했으나 getUserId() 등의 메서드가 존재하지 않아 사용할 수 없었음.
- 해당 코드는
Long user = (Long) joinPoint.getArgs()[0].getUserId();
위와 같은 형태로 작성하고자 했는데, 이러한 코드에는 타입 관련 문제가 생김.
메서드 체이닝의 한계로 joinPoint.getArgs()[0]의 타입을 AuthUser로 캐스팅해주어야 getUserId()로 사용자의 아이디를 추출할 수 있는데, 이 경우 AuthUser 클래스에 getUserId() 메서드가 존재하지 않는 경우에는 위의 형태로 코드를 작성할 수 없음.

```
Object[] args = joinPoint.getArgs();
AuthUser authUser = (AuthUser) args[0];
Long user = authUser.getUserId();
```
위와 같이 각 변수의 명확한 타입을 구분하고 필요한 데이터를 사용할 수 있도록 해결했으며, 가독성과 디버깅 시 용이하도록 코드를 변경하였음.

3) QueryDSL을 적용하여 일정 다건 조회 시에 쿼리문에서 where절의 사용에 대해 고민함.
- 주어진 요구사항은 일정의 제목 키워드, 일정 생성일의 기간 검색, 일정 생성자의 닉네임으로 검색할 수 있게 해야 한다는 요구사항이었음. 이에 대해서 각 조건은 OR 조건으로 성립해야 한다고 이해했기 때문에 쿼리문 코드를 아래와 같이 작성함.
```
.where(titleContain(title)
.where(createAtBetween(startDate, endDate))
.where(nicknameContain(nickname))
)
```
위 코드의 경우에는 타이틀, 기간 검색, 닉네임 중 한 조건이라도 만족하는 결과가 있다면 그 결과를 출력함. 그러나 요구 사항은 타이틀과 기간 검색, 닉네임을 모두 받지만 그 값이 없는 경우에는 무시하는 결과를 출력하기를 원하고 있기 때문에 아래와 같이 AND 조건으로 결합된 쿼리로 수정함.
```
.where(
	titleContain(title),
	createAtBetween(startDate, endDate),
	nicknameContain(nickname)
)
```
