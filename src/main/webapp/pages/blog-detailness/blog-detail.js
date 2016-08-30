/**
 * Created by LWang on 2016/7/15.
 */
/**
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
                        var replyComment = data[i]._replyComment;
                        console.log("replyComment"+replyComment);
                        var replyNum = 0;
                        if(replyComment!=null){
                            replyNum = replyComment.length;
                        }
                        var replyhtml = "";
                        console.log("commentId"+commentId);
                        for(var j=0;j<replyNum;j++){
                            var replyContent = replyComment[j]._content;
                            var replyCommentTime = replyComment[j].commentTime;
                            replyhtml+='<div class="row"><div class="col-md-2"></div><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                                '<img src="../../img/lww.png" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                                '<div class="col-md-8 " > <div id="comment_content"> </div> ' +
                                '<p class="bg-primary">爱笑的眼睛&nbsp;&nbsp;&nbsp;2016-09-09</p> ' +
                                '<p>'+replyContent+'</p> </div> </div> ' +
                                '<div class="row"> <div class="col-md-10"> </div> ' +
                                '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentArea(\''+commentId+'\',\''+fromUserId+'\')">回复</a> </div>' +
                                '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentArea(\''+commentId+'\')">关闭</a> </div> </div>' +
                                '<div id="showCommentArea'+commentId+'"></div> ' ;
                        }
                        html+='<div class="row"><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                            '<img src="../../img/lww.png" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                            '<div class="col-md-10 " > <div id="comment_content"> </div> ' +
                            '<p class="bg-primary">爱笑的眼睛&nbsp;&nbsp;&nbsp;2016-09-09</p> ' +
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
    init: function() {

        themeApp.getblog();
        themeApp.backToTop();
        themeApp.validateAndAddComment();
        themeApp.showComment();
    }

}


function getLocalTime(nS) {
    var date = new Date(nS);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    return Y+M+D;
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
                var html="";
                html+='<div class="row"><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                    '<img src="../../img/lww.png" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                    '<div class="col-md-10 " > <div id="comment_content"> </div> ' +
                    '<p class="bg-primary">爱笑的眼睛&nbsp;&nbsp;&nbsp;2016-09-09</p> ' +
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
    jQuery.ajax({
        type:"GET",
        url:"../../comment/add_comment.do",
        async:false,
        data:{blogId:blogId,blog_comment:comment,cid:cid,tid:"hii"},
        dataType:"json",
        success:function(data){
            console.log("addComment"+data);
            var commentNum = data.length;
            for(var i=0; i<commentNum;i++){
                var comment = data[i]._content;
                var commentId = data[i]._commentId;
                var html="";
                html+='<div class="row"><div class="col-md-2"></div><div class="col-md-2"> <div href="#" class="thumbnail"> ' +
                    '<img src="../../img/lww.png" style="width: 50px ;height: 50px" alt="user-image"> </div> </div> ' +
                    '<div class="col-md-8 " > <div id="comment_content"> </div> ' +
                    '<p class="bg-primary">爱笑的眼睛&nbsp;&nbsp;&nbsp;2016-09-09</p> ' +
                    '<p>'+comment+'</p> </div> </div> ' +
                    '<div class="row"> <div class="col-md-10"> </div> ' +
                    '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="showCommentArea(\''+commentId+'\')">回复</a> </div>' +
                    '<div class="col-md-1 " > <a href="javascript:void(0)" onclick="hideCommentArea(\''+commentId+'\')">关闭</a> </div> </div>' +
                    '<div id="showCommentArea'+commentId+'"></div> ' +
                    '<div id="showComment'+commentId+'"></div> ' +
                    '<hr>';
            }

            $("#blog-reply-comment-content").val("");//评论框中内容清空
            hideCommentArea(commentId);//评论框去掉
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
            '<textarea id="blog-reply-comment-content" class="form-control" rows="3" placeholder="说点什么吧~~" ></textarea> ' +
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

function showReplyComment(commentId){

    var showCommentId = "showComment"+commentId;
    console.log("showCommentId"+showCommentAreaId);
    var obj = document.getElementById(showCommentId);
    $(obj).html("");
}
/*===========================
 2. Initialization
 ==========================*/
$(document).ready(function(){
    themeApp.init();
});