let index={
	init: function(){
		$("#btn-save").on("click", ()=>{ // (function(){} ->) ()=>{} this를 바인딩 하기 위해
			this.save();
		});
		/*$("#btn-login").on("click", ()=>{ // (function(){} ->) ()=>{} this를 바인딩 하기 위해
			this.login();
		});*/
	}, // btn-save 버튼 클릭하면 save 함수 실행
	/*
	let _this = this;
	
	init: function(){
		$("#btn-save").on("click", function(){ // function(){} 을 쓸려면
			_this.save();	
		})
	}	
	*/
	
		/*login: function(){
		
		let data={
			username: $("#username").val(),
			password: $("#password").val()
		};
		
		$.ajax({
			type: "post",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("로그인이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},*/
	
	save: function(){
		// alert("user의 save함수 호출")
		
		let data={
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}; // 자바스크립트 오브젝트
		
		// console.log(data);
		
		
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
		// ajax 을 사용하는 이유
		// 1. html, data를 한 번에 리턴해주는 서버 하나만 만들면 된다
		//	   - 서버를 통일시켜 서버를 두개(브라우저, 앱에서 작동하는) 만드는 일이 없음
		// 2. 비동기 통신 : 순서에 상관없이 처리
		
		// ajax 호출 시 default가 비동기 호출
		$.ajax({
			// 회원가입 수행 요청
			type: "post",
			url: "/auth/joinProc", // +"/join" post -> insert 알아서 인식
			data: JSON.stringify(data), // JSON 문자열로 변환 // http body 데이터
			contentType: "application/json; charset=utf-8", //body 데이터 타입(MIME)
			dataType: "json"
			// 요청 -> 서버로부터 응답이 왔을 때 기본적으로 모두 문자열( json이라면=>javascript 오브젝트 변경)
			// 응답의 결과 (javascript 오브젝트) -> 함수의 파라미터로 전달
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			console.log(resp); // UserApiController -> return(JSON으로 변환) -> resp
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	}
} // 오브젝트
// ->
index.init(); // 함수 호출