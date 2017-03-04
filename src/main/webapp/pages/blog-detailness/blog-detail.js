/**
 *
 * Created by LWang on 2016/7/15.
 */

/*====================================================
 TABLE OF CONTENT
 1. function declearetion
 2. Initialization
 ====================================================*/

/*===========================
 1. function declearetion
 ==========================*/
var themeApp = {
    backToTop: function() {
        $(window).scroll(function(){
            if ($(this).scrollTop() > 100) {
                $('#back-to-top').fadeIn();
            } else {
                $('#back-to-top').fadeOut();
            }
        });
        $('#back-to-top').on('click', function(e){
            e.preventDefault();
            $('html, body').animate({scrollTop : 0},1000);
            return false;
        });
    },
    getblog:function(){
        var id = GetRequest();
        var url = "../../blog/blog-detail/"+id+".do";
        jQuery.ajax({
            type:"GET",
            url:url,
            async:false,
            data:"",
            dataType:"json",
            success:function(data){
                console.log("getBlog"+data);
               /* blocks = aBlogBlock(data._id,data._title,getLocalTime(data._create_at),data._content);
                $("#block").append(blocks);*/
                $("#blog-id").val(data._id);
                $("#blog-title").text(data._title);
                $("#blog-create-at").text(getLocalTime(data._create_at));
                $("#blog-content").html(data._content);
            }
        });
    },
    showComment:function(){
        var blogId = $("#blog-id").val();
        $.ajax({
            type:"GET",
            url:"../../comment/show_comment.do",
            data:{blogId:blogId},
            async:false,
            dataType:"json",
            success:function(data){
                console.log(data);
                var html="";
                    var commentNum = data.length;
                    console.log("commentNum"+commentNum);
                    var i;
                    for( i=0; i<commentNum;i++){
                        var comment = data[i]._content;
                        var commentId = data[i]._commentId;
                        var fromUserId = data[i]._fromUserId;
                        var fromUserName = data[i]._fromUserName;
                        var commentTime = data[i]._commentTime;
                        var replyComment = data[i]._replyComment;
                        var fromUserIcon = data[i]._fromUserIcon;
                        console.log("replyComment"+replyComment);
                        var replyNum = 0;
                        if(replyComment!=null){
                            replyNum = replyComment.length;
                        }
                        if(fromUserIcon==null){
                            fromUserIcon = "../../img/lww.png";
                        }
                        var replyhtml = "";
                        console.log("commentId"+commentId);
                        for(var j=0;j<replyNum;j++){
                            var replyId = replyComment[j]._id;
                            var replyContent = replyComment[j]._content;
                            var toUserName = replyComment[j]._toUserName;
                            var toUserId = replyComment[j]._toUserId;
                            var replyFromUserName = replyComment[j]._fromUserName;
                            var replyFromUserId = replyComment[j]._fromUserId;
                            var replyFromUserIcon = replyComment[j]._fromUserIcon;
                            var replyCommentTime = replyComment[j].commentTime;
                            if(replyFromUserIcon == null){
                                replyFromUserIcon = "../../img/lww.png";
                            }
                            replyhtml+='<div class="row"><div class="col-md-2"></div><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                                '<img src="'+replyFromUserIcon+'" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                                '<div class="col-md-8 " > <div id="comment_content"> </div> ' +
                                '<p class="bg-primary">&nbsp;&nbsp;'+replyFromUserName+'&nbsp;&nbsp;回复&nbsp;&nbsp;'+toUserName+'&nbsp;&nbsp;&nbsp;'+getLocalTime(replyCommentTime)+'</p> ' +
                                '<p>'+replyContent+'</p> </div> </div> ' +
                                '<div class="row"> <div class="col-md-10"> </div> ' +
                                '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentReplyArea(\''+commentId+'\',\''+replyFromUserId+'\',\''+replyId+'\')">回复</a> </div>' +
                                '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentReplyArea(\''+replyId+'\')">关闭</a> </div> </div>' +
                                '<div id="showCommentReplyArea'+replyId+'"></div> ' ;
                        }
                        html+='<div class="row"><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                            '<img src="'+fromUserIcon+'" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                            '<div class="col-md-10 " > <div id="comment_content"> </div> ' +
                            '<p class="bg-primary">&nbsp;&nbsp;'+fromUserName+'&nbsp;&nbsp;&nbsp;'+getLocalTime(commentTime)+'</p> ' +
                            '<p>'+comment+'</p> </div> </div> ' +
                            '<div class="row"> <div class="col-md-10"> </div> ' +
                            '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentArea(\''+commentId+'\',\''+fromUserId+'\')">回复</a> </div>' +
                            '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentArea(\''+commentId+'\')">关闭</a> </div> </div>' +
                            '<div id="showCommentArea'+commentId+'"></div> ' +
                                '<div>'+replyhtml+'</div>'+
                            '<hr>';
                        console.log( '<div id="showCommentArea'+commentId+'"></div> ');
                    }
                    $("#blog-commemt-block").append(html);

            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                console.log("error");
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            }
        });
    },
    validateAndAddComment:function(){
    //参数为评论，博客信息,用户信息在后台获得

        $("#addComment").on('click',function(){
            var comment=encodeURI($("#blog-comment-content").val());
            console.log("validateAndAddComment.comment"+comment);
            //对评论的内容作出拦截
            jQuery.ajax({
                type:"POST",
                url:"../../comment/validate_comment.do",
                data:{blog_comment:comment},
                dataType:"text",
                success:function(data){
                    console.log("validateAndAddComment.data"+data);
                    if(data!="OK"){
                        $("#blog-comment-content").val(data);
                    }else{
                        console.log("validateAndAddComment.heh");
                        addComment();
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    console.log("error");
                    console.log(XMLHttpRequest.status);
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                }
            });
        })
},
    register:function () {
        $("#register").on('click',function(){
            //首先应该进行校验，包括用户名是否注册过，表单数据是否符合规范
            //如果不符合规范，直接返回，不继续执行以下操作
            if(!$("#register-form").valid()) return;
            userRegister();
        })

    },
    login:function () {
        $("#login").on('click',function () {
            //校验，用户名和密码的长度
            //todo
            userLogin();
        })
    },

    init: function() {

        themeApp.getblog();
        themeApp.backToTop();
        themeApp.validateAndAddComment();
        themeApp.showComment();
        themeApp.register();
        themeApp.login();
    }

}


function getLocalTime(nS) {
    var date = new Date(nS);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    H = date.getHours()+':';
    MM = date.getMinutes()+':';
    S = date.getSeconds();
    return Y+M+D+H+MM+S;
}
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("=");
    }
    return strs[1];
}
function replyCommentValidateAndAdd(commentId,fromUserId){
    //参数为评论，博客信息,用户信息在后台获得
        var comment=encodeURI($("#blog-reply-comment-content").val());
        console.log("replyCommentValidateAndAdd.comment"+comment);
        //对评论的内容作出拦截
        jQuery.ajax({
            type:"POST",
            url:"../../comment/validate_comment.do",
            data:{blog_comment:comment},
            dataType:"text",
            success:function(data){
                console.log("replyCommentValidateAndAdd.data"+data);
                if(data!="OK"){
                    $("#blog-reply-comment-content").val(data);
                }else{
                    console.log("replyCommentValidateAndAdd.heh");
                    addReplyComment(commentId,fromUserId);
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                console.log("error");
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            }
        });
}
function addComment(){
    var blogId=$("#blog-id").val();//获取博客的信息（ID）
    var comment=encodeURI($("#blog-comment-content").val());
    jQuery.ajax({
        type:"GET",
        url:"../../comment/add_comment.do",
        async:false,
        data:{blogId:blogId,blog_comment:comment},
        dataType:"json",
        success:function(data){
            console.log("addComment"+data);
            var commentNum = data.length;
            for(var i=0; i<commentNum;i++){
                var comment = data[i]._content;
                var commentId = data[i]._commentId;
                var fromUserId = data[i]._fromUserId;
                var fromUserName = data[i]._fromUserName;
                var commentTime = data[i]._commentTime;
                var replyComment = data[i]._replyComment;
                var fromUserIcon = data[i]._fromUserIcon;
                if(fromUserIcon==null){
                    fromUserIcon = "../../img/lww.png";
                }
                var html="";
                html+='<div class="row"><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                    '<img src="'+fromUserIcon+'" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                    '<div class="col-md-10 " > <div id="comment_content"> </div> ' +
                    '<p class="bg-primary">&nbsp;&nbsp;'+fromUserName+'&nbsp;&nbsp;&nbsp;'+getLocalTime(commentTime)+'</p> ' +
                    '<p>'+comment+'</p> </div> </div> ' +
                    '<div class="row"> <div class="col-md-10"> </div> ' +
                    '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentArea(\''+commentId+'\')">回复</a> </div>' +
                    '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentArea(\''+commentId+'\')">关闭</a> </div> </div>' +
                    '<div id="showCommentArea'+commentId+'"></div> ' +
                    '<hr>';
            }
            $("#blog-comment-content").val("");
            $("#blog-commemt-block").append(html);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log("error");
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    });
}
function addReplyComment(commentId,fromUserId){
    var blogId=$("#blog-id").val();//获取博客的信息（ID）
    var comment=encodeURI($("#blog-reply-comment-content").val());
    var cid= commentId;//获取父评论的id
    var tid = fromUserId; //获取的是父评论的用户ID
    console.log("tid"+tid);
    jQuery.ajax({
        type:"GET",
        url:"../../comment/add_comment.do",
        async:false,
        data:{blogId:blogId,blog_comment:comment,cid:cid,tid:tid},
        dataType:"json",
        success:function(data){
            console.log("addComment"+data);
            var commentNum = data.length;
            var i;
            var html="";
            for( i=0; i<commentNum;i++){
                var comment = data[i]._content;
                var commentId = data[i]._commentId;
                var fromUserId = data[i]._fromUserId;
                var fromUserName = data[i]._fromUserName;
                var commentTime = data[i]._commentTime;
                var replyComment = data[i]._replyComment;
                var fromUserIcon = data[i]._fromUserIcon;
                console.log("replyComment"+replyComment);
                var replyNum = 0;
                if(replyComment!=null){
                    replyNum = replyComment.length;
                }
                if(fromUserIcon==null){
                    fromUserIcon = "../../img/lww.png";
                }
                var replyhtml = "";
                console.log("commentId"+commentId);
                for(var j=0;j<replyNum;j++){
                    var replyId = replyComment[j]._id;
                    var replyContent = replyComment[j]._content;
                    var toUserName = replyComment[j]._toUserName;
                    var toUserId = replyComment[j]._toUserId;
                    var replyFromUserName = replyComment[j]._fromUserName;
                    var replyFromUserId = replyComment[j]._fromUserId;
                    var replyCommentTime = replyComment[j].commentTime;
                    var replyFromUserIcon = replyComment[j]._fromUserIcon;
                    if(replyFromUserIcon == null){
                        replyFromUserIcon = "../../img/lww.png";
                    }
                    replyhtml+='<div class="row"><div class="col-md-2"></div><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                        '<img src="'+replyFromUserIcon+'" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                        '<div class="col-md-8 " > <div id="comment_content"> </div> ' +
                        '<p class="bg-primary">&nbsp;&nbsp;'+replyFromUserName+'&nbsp;&nbsp;回复&nbsp;&nbsp;'+toUserName+'&nbsp;&nbsp;&nbsp;'+getLocalTime(replyCommentTime)+'</p> ' +
                        '<p>'+replyContent+'</p> </div> </div> ' +
                        '<div class="row"> <div class="col-md-10"> </div> ' +
                        '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentReplyArea(\''+commentId+'\',\''+replyFromUserId+'\',\''+replyId+'\')">回复</a> </div>' +
                        '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentReplyArea(\''+replyId+'\')">关闭</a> </div> </div>' +
                        '<div id="showCommentReplyArea'+replyId+'"></div> ' ;
                }
                html+='<div class="row"><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                    '<img src="'+fromUserIcon+'" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                    '<div class="col-md-10 " > <div id="comment_content"> </div> ' +
                    '<p class="bg-primary">&nbsp;&nbsp;'+fromUserName+'&nbsp;&nbsp;&nbsp;'+getLocalTime(commentTime)+'</p> ' +
                    '<p>'+comment+'</p> </div> </div> ' +
                    '<div class="row"> <div class="col-md-10"> </div> ' +
                    '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentArea(\''+commentId+'\',\''+fromUserId+'\')">回复</a> </div>' +
                    '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentArea(\''+commentId+'\')">关闭</a> </div> </div>' +
                    '<div id="showCommentArea'+commentId+'"></div> ' +
                    '<div>'+replyhtml+'</div>'+
                    '<hr>';
                console.log( '<div id="showCommentArea'+commentId+'"></div> ');
            }

            $("#blog-reply-comment-content").val("");//评论框中内容清空
            hideCommentReplyArea(replyId);//评论框去掉
            $("#blog-commemt-block").html("");
            $("#blog-commemt-block").append(html);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log("error");
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    });
}
function showCommentArea(commentId,fromUserId){
    var showCommentAreaId = "showCommentArea"+commentId;
    console.log("showCommentAreaId"+showCommentAreaId);
    var obj = document.getElementById(showCommentAreaId);
    var commentArea = '<form role="form"> <div class="form-group"> ' +
            '<textarea id="blog-reply-comment-content" class="form-control" rows="3" placeholder="说点什么吧~~" onclick="isLoginUser()"></textarea> ' +
            '</div> </form> <div class="row"> <div class="col-md-10"> </div> ' +
            '<div class="col-md-2"> <button  class="btn btn-primary blue" onclick="replyCommentValidateAndAdd(\''+commentId+'\',\''+fromUserId+'\')">提交</button> ' +
            '</div> </div>';
    $(obj).html(commentArea);
}

function showCommentReplyArea(commentId,fromUserId,replyId){
    var showCommentReplyAreaId = "showCommentReplyArea"+replyId;
    console.log("showCommentReplyAreaId"+showCommentReplyAreaId);
    var obj = document.getElementById(showCommentReplyAreaId);
    var commentArea = '<form role="form"> <div class="form-group"> ' +
        '<textarea id="blog-reply-comment-content" class="form-control" rows="3" placeholder="说点什么吧~~" onclick="isLoginUser()"></textarea> ' +
        '</div> </form> <div class="row"> <div class="col-md-10"> </div> ' +
        '<div class="col-md-2"> <button  class="btn btn-primary blue" onclick="replyCommentValidateAndAdd(\''+commentId+'\',\''+fromUserId+'\')">提交</button> ' +
        '</div> </div>';
    $(obj).html(commentArea);
}
function hideCommentArea(commentId){
    var showCommentAreaId = "showCommentArea"+commentId;
    console.log("showCommentAreaId"+showCommentAreaId);
    var obj = document.getElementById(showCommentAreaId);
    $(obj).html("");
}
function hideCommentReplyArea(replyId){
    var showCommentReplyAreaId = "showCommentReplyArea"+replyId;
    console.log("showCommentReplyArea"+showCommentReplyAreaId);
    var obj = document.getElementById(showCommentReplyAreaId);
    $(obj).html("");
}
function toRegister() {
    $("#login-responsive").modal('hide');
    $("#register-responsive").modal('show');
}

function isLoginUser(){
    //alert("click textarea!!!");
    //1.一个判断用户是否登录的请求（获取session的信息）
    //2.没登陆，评论区域置为不可用，弹出模态框，显示注册登录的界面
    //3.用户是登录过的，不做任何操作
    jQuery.ajax({
        type:"GET",
        url:"../../user/isLogin.do",
        data:"",
        dataType:"json",
        success:function(data){
            console.log("data"+data);
            if(data==false){
                $("#login-responsive").modal('show');
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log("error");
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    });
}

function userRegister() {

        var  username = $("input[name='register_username']").val();
        var password = $("#register_password").val();
        console.log("register_username"+username);
        console.log("register_password"+password);
        jQuery.ajax({
            url:"../../user/register.do",
            type:"GET",
            data:{username:username,password:password},
            dataType:"json",
            success:function (data,status) {
                console.log("data"+data);
                console.log("status"+status);
                if(data == true){
                    //注册成功
                    $("#register-responsive").modal('hide');
                   // $("#loginModalLabel").val("");
                    $("#login-responsive").modal('show');
                   // $("#loginModalLabel").val("注册成功，请登录");
                }else {
                    //注册失败
                    $("#registerModelLaben").val("注册失败，请重新注册");
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                console.log("error");
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            }

        })

}
function userLogin() {
    var  username = $("input[name='login-username']").val();
    var password = $("#login-password").val();
    console.log("register_username"+username);
    console.log("register_password"+password);
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
                $("#login-responsive").modal('hide');
            }else {
                //登录失败
                $("#login-responsive").modal('show');
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log("error");
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }

    })
}
//微博的登录的回调函数
function login(o) {
    //获得用户的昵称
    var name = o.screen_name;
    //获得用户的头像url
    var imageUrl = o.profile_image_url;
    var thirdPart = "weibo";
    console.log("name" +name);
    console.log("imageUrl"+imageUrl);
    jQuery.ajax({
        url:"../../user/thirdPart_login.do",
        type:"GET",
        data:{username:name,imageUrl:imageUrl,thirdPart:thirdPart },
        dataType:"json",
        success:function (data,status) {
            console.log("data"+data);
            console.log("status"+status);
            if(data == "1"){
                //登录成功
                $("#login-responsive").modal('hide');
            }else {
                //登录失败
                $("#login-responsive").modal('hide');
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log("error");
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }

    })

}
//微博退出的回调函数
function logout() {
    $("#login-responsive").modal('hide');
}
//注册框的校验函数
$('#register-form').validate({
    errorElement: 'span', //default input error message container
    errorClass: 'help-inline', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",
    rules: {
        "register_username": {
            required: true,
            maxlength: 10,
            remote: {
                type: "GET",
                dataType: "json",
                url: "../../user/checkRegisterUserName.do",
                data: {
                    register_username: function () {
                        return $("input[name='register_username']").val();
                    }
                }
            }
        },

        "register_password": {
            required: true
        }
    },
    messages: { // custom messages for radio buttons and checkboxes
        "register_username": {
            required: "用户名不能为空",
            maxlength:jQuery.format("长度不能超过{0}"),
            remote:"该用户名已存在"
        },
        "register_password": {
            required: "密码不能为空"
        }
    },

    invalidHandler: function (event, validator) { //display error alert on form submit
        //success1.hide();
        //error1.show();
        //App.scrollTo(error1, -200);
    },

    highlight: function (element) { // hightlight error inputs
        $(element)
            .closest('.help-inline').removeClass('ok'); // display OK icon
        $(element)
            .closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
    },

    unhighlight: function (element) { // revert the change dony by hightlight
        $(element)
            .closest('.control-group').removeClass('error'); // set error class to the control group
    },

    success: function (label) {
        label
            .addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
            .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
    }

    //submitHandler: function (form) {
    //    success1.show();
    //    error1.hide();
    //}
});


/*===========================
 2. Initialization
 ==========================*/
$(document).ready(function(){
    themeApp.init();
});