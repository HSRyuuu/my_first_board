# 게시판

### 사용 기술
Java 11, SpringBoot 2.7.10, Thymeleaf

>#### Todo
>- DB 적용
>- JPA 적용
<br>

# 주요 기능

### 회원 관리(Member)
회원가입 / 로그인 / 필터(Spring Interceptor)
- 회원 CRUD 구현 (회원가입, 회원정보 열람, 수정, 삭제)
- 세션쿠키를 이용해서 현재 로그인 되어있는 상태인지 확인
- 로그인 없이도 이용할수 있는페이지, 로그인 회원만 이용할 수 있는 페이지 구분
- 비로그인 상태로 회원 전용 페이지에 들어가려 할때, 로그인페이지로 이동
- 로그아웃 시 세션 만료

### 글 (Post)
제목, 내용, 작성자 검색 / 수정, 삭제 / 정렬
- 글 관련 CRUD 구현 ( 글쓰기, 열람, 수정, 삭제 )
- 제목, 내용, 작성자로 각각 검색 가능
- 세션을 통해 로그인 사용자 확인 후 본인의 글일때만 수정, 삭제 가능
- 조회수 기능 ( 본인이 본인 글 열람시 조회수 증가x)



### 검증(Validation)
Spring Bean Validation
- 모든 사용자 입력 발생 시 Validation 진행 

### 메시지
- messages.properties / errors.properties 파일을 이용해 메시지 관리
- 국제화 적용 (messages_en.properties, errors_en.properties) 


### Front View
Thymeleaf, Html, bootstrap

>TODO
>- 댓글, 대댓글
>- 카테고리, 해시태그
>- 조회수정렬, 최신 순 정렬
>- 페이징 
