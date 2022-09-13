# SpringMVC2
Spring MVC패턴으로 만든 웹페이지 
# ✨SPRING_STUDY
✔스프링 학습 관련 레파지토리입니다.✔
<hr>
<b>📝파일명 : 프로젝트 진행 내용</b><br>
- form : 타임리프를 사용한 입력 form view단<br>
- message : 메세지 국제화<br>
- validation : 스프링에서 지원하는 검증 방법 + 요구 사항 추가로 인한 컨트롤러 검증 추가 및 타임리프 error 추가<br>
- login : 회원가입 + 로그인처리(쿠키,세션 개념이용)<br>
- exception : 예외 처리 + 에러페이지 추가
<hr>
<b>🎈학습 내용</b><br>
<b>- 타임리프 체크박스 트릭 사용</b> : 타임리프에서의 체크박스는 체크를 하지 않는다면 false가 아닌 null이 들어간다. 이를 방지하기 위한 _트릭을 사용 및 학습<br>
<b>- 타임리프 입력 form에서의 사용</b> : input / checkBox / selectBox 에서의 타임리프 작성법<br>
<b>- 검증 추가</b> : 자바 언어를 이용한 검증 추가 + 타임리프를 이용한 검증 에러 내용<br>
<b>- BindingResult를 이용한 검증추가</b> : BindingResult를 요청파라미터에 넣어서 코드 리펙토링 진행  <br>
<b>- BindingResult를 사용한 error를 view단에서의 사용</b> : BindingResult에서 넘어온 globalError 나 fieldError 를 타임리프 #fields.globalErrors와 *{에러 필드명}사용  <br>
<b>- 컨틀롤러와 검증 로직 분리</b> : Interface Validator를 지원받는 클래스를 만들어서 검증 로직을 컨트롤러와 분리하였다<br>
<b>- Bean Validator를 사용한 검증 추가</b> : @NotNull / @NotBlank / @Range 등등 Bean validator를 이용한 검증 추가<br>
<b>- BeanValidation의 groups 기능을 사용</b> : 인터페이스 제작후 @Validated(value="") 로 사용<br>
<b>- DTO의 유형인  ItemSaveForm, ItemUpdateForm 를 사용</b> : 폼 데이터 전달을 위한 별도의 객체 사용<br>
<b>- loginService 로직 제작</b> : 로그인 성공과 실패를 service에서 제작 후 controller에서 사용<br>
<b>- 회원의 고유 id값을 Cookie에 저장</b> : 로그인 성공시 memberId 를 Cookie에 저장 로그아웃은 Cookie의 생명주기를 0으로 제작<br>
<b>- Session추가</b> : 쿠키의 보안성 문제로 session생성후 session저장소에 member객체 저장<br>
<b>- Session 생명주기</b> : 사용자가 서버에 최근에 요청한 시간을 기준으로 30분으로 유지 server.servlet.session.timeout=60<br>
<b>-서블릿 필터 적용</b> : 서블릿 필터를 이용한 미로그인자 인증체크 -> 로그인화면으로 이동시킴<br>
<b>-스프링 인터셉터 적용</b> : 스프링에서 지원하는 HandlerInterceper를 사용하여 인증체크 진행 <br>
<b>-애러페이지 추가</b> : 스프링부트에서 지원하는 에러페이지 적용 <br>
<b>-스프링 타입 컨버터</b> : conversionService를 이용한 문자열과 객체의 convert적용 <br>
<b>-파일 업로드</b> : MultipartFormData을 이용한 파일 전송 및 다운로드 <br>

- <hr>
<b>🎈학습에 대하여 느낌점</b><br>
-validation(검증)을 bean validator를 이용하여 검증하니까 굉장히 쉽다.<br>
-중요! @PathVariable로 들어온 매개변수 redirect:/ 사용시 바로 사용가능! 또한 uri상 @PathVariable의 이름과 매개변수의 이름이 같아야한다!<br>
-쿠키에는 민감한 정보를 넣으면 안됨 --> 세션 사용(보안증가)<br>
-서블릿 필터를 사용하려면 webconfig에 @bean등록을 해주어야한다 <br>
-스프링 인터셉터를 사용하려면 webconfig에 webMvcConfigurer를 지정받아서 사용한다.<br>
-서블릿 필터와 스프링 인터셉터는 웹과 관련된 공통 관심사를 해결하기 위한
