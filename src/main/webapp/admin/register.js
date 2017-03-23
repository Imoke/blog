//todo 关于用户名已存在的问题还没解决
$('#registerBtn').on('click',function() {
	var username = $("input[name='username']").val();
	var password = $("input[name='password']").val();
	var passwordOK = $("input[name='passwordOK']").val();
	//var email = $("input[name='email']").val();
	jQuery.ajax({
		url: "../../user/register.do",
		type: "GET",
		data: {username: username, password: password},
		dataType: "json",
		success: function (data, status) {
			console.log("data" + data);
			console.log("status" + status);
			if (password == passwordOK) {
				if (data == true) {
					//注册成功
					alert("注册成功");
					window.location.href = '../../admin/login.html';
				} else {
					//注册失败
					alert("注册失败");
				}
			} else {
				alert("输入的密码不一致");
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			console.log("error");
			console.log(XMLHttpRequest.status);
			console.log(XMLHttpRequest.readyState);
			console.log(textStatus);
		}

	});
});
