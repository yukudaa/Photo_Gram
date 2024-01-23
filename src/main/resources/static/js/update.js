// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault(); // 폼태크 액샨을 막기!!

	let data = $("#profileUpdate").serialize();

	console.log(data);

	$.ajax({
		type: "put",
		url: '/api/user/${userId}',
		data: data,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		dataType: "json"
	}).done(res => { // HttpStatus 상태코드 200번대
		console.log("update 성공");
		location.href = '/user/${userId}'; // 수정하면 메인으로
	}).fail(error => { // HttpStatus 상태코드 200번대 아닐 때
		if (error.data == null) {
			alert(error.responseJSON.message);
		} else {
			alert(JSON.stringify(error.responseJSON.data));
		}
	});

}