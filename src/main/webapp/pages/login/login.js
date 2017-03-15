//function loginbtClick() {
	$('#loginBtn').on('click', function () {
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();

		jQuery.ajax({
			url:"../../user/login.do",
			type:"GET",
			data:{username:username,password:password},
			dataType:"json",
			success:function (data,status) {
				console.log("data"+data);
				console.log("status"+status);
				if(data == "0"){
					//登录成功
					alert("login success!");
                    window.location.href = 'manageBlog.html';
				}else {
					//登录失败
					alert("login fail!");
					window.location.href = 'login.html';
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				console.log("error");
				console.log(XMLHttpRequest.status);
				console.log(XMLHttpRequest.readyState);
				console.log(textStatus);
			}

		})
	});
