### # JPA를 활용한 upgrade 일정 관리 앱 서버 만들기

- 일정 관리 프로그램에서 JPA로 CRUD를 구현하여, 객체 지향적으로 데이터를 다룹니다.
- JPA를 활용하여 DB를 관리하고 영속성에 대해 이해할 수 있습니다.

#### # API 명세 - Schedule

|기능|HTTP 메서드|URL|request|response|
|------|---|---|---|---|
|일정 추가|POST|/schedules|요청 body|scheduleId, scheduleUserName, scheduleTitle, scheduleContents|
|일정 페이징 조회|GET|/schedules|요청 param|scheduleUserName, scheduleTitle, scheduleContents|
|일정 단건 조회|GET|/schedules/{scheduleId}|요청 param|scheduleTitle, scheduleContents|
|일정 수정|PUT|/schedules/{scheduleId}|요청 param, 요청 body|scheduleUserName, scheduleTitle, scheduleContents|
|일정 삭제|DELETE|/schedules/{scheduleId}|요청 param|-|

#### # API 명세 - Comment

|기능|HTTP 메서드|URL|request|response|
|------|---|---|---|---|
|댓글 추가|POST|/schedules/{scheduleId}/comments|요청 param, 요청 body|commentId, commentUserName, commentContents|
|댓글 전체 조회|GET|/schedules/comments|-|commentUserName, commentContents|
|댓글 단건 조회|GET|/schedules/comments/{commentId}/{scheduleId}|요청 param|commentUserName, commentContents|
|댓글 수정|PUT|/schedules/comments/{commentId}|요청 param, 요청 body|commentUserName, commentContents|
|댓글 삭제|DELETE|/schedules/comments/{commentId}|요청 param|-|
