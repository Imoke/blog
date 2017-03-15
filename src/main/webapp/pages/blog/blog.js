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

    addTop:function(){
        console.log("1");
        $("#top").load("../../pages/top/top.html");
        console.log("2");
        /*if($("#header").hasClass("headerDark")) {
            console.log("3");
            $("#header").removeClass("headerDark").addClass("headerDark headSkin");
        }*/

        console.log("4");
    },

    showBlogs:function(){
        var page  = GetRequest();
        var url = "../../blog/"+page+".do";
        var pageNum = getAllPageNum();
        jQuery.ajax({
            type:"GET",
            url:url,
            data:"",
            dataType:"json",
            success:function(data){
                var post_num = data.length;
                var blocks = '';
                for(var i=0;i<post_num;i++){
                    var tags = data[i]._tags;
                    blocks += aBlogBlock(data[i]._id,data[i]._title,getLocalTime(data[i]._create_at),data[i]._describe,tags,data[i]._fig);
                }
                $("#block").append(blocks);
                if(page == 1){
                    if(post_num<5){
                        $("#page").append(noLeftRightPage(page,pageNum));
                    }else {
                        $("#page").append(noLeftPage(page, pageNum));
                    }
                }else{
                    if(page >= pageNum){
                        $("#page").append(noRightPage(page,pageNum));
                    }else{
                        $("#page").append(bothPage(page,pageNum));
                    }
                }


            }
        });
    },
    init: function() {
        themeApp.showBlogs();
        themeApp.backToTop();
    }
}
function aBlogBlock(id,title,createTime,describe,tags,fig){
    var tagblock = "";
    var field = "http://myliuwang.com";
    var path1 = "/resources/upload/";
    var path = "/root/blogfile/fig/weixin.jpg";
    if(tags!=null) {
        for (var i = 0; i < tags.length; i++) {
            tagblock += '<a href="../blog-classification/blog-class.html?name=' + tags[i]._name_eng + '">' + tags[i]._name + '&nbsp;&nbsp;</a>';
        }
        if(fig!=null){
            return '<article id=' + id + ' class="post"> <div class="post-head"><h1 class="post-title">' +
                '<a href="#"></a>' + title + '</h1> <div class="post-meta"><time class="post-date">发表于' + createTime + '</time> ' +
                '</div> </div><div class="featured-media"> <a href="#"><img src="'+field+''+path1+''+fig+'" ></a>' +
                ' </div><div class="post-content"> <p>' + describe + '</p> </div> <div class="post-permalink"> ' +
                '<a href="../blog-detailness/blog-detail.html?id=' + id + '" class="btn btn-default">阅读全文</a> </div>' +
                ' <footer class="post-footer clearfix"> <div class="pull-left tag-list"> <i class="fa fa-folder-open-o"></i> ' + tagblock + '</div>' +
                '<div class="pull-right share"> </div> </footer> </article>'
        }else {
            return '<article id=' + id + ' class="post"> <div class="post-head"><h1 class="post-title"><a href="#"></a>' + title + '</h1> <div class="post-meta"><time class="post-date">发表于' + createTime + '</time> </div> </div><div class="post-content"> <p>' + describe + '</p> </div> <div class="post-permalink"> <a href="../blog-detailness/blog-detail.html?id=' + id + '" class="btn btn-default">阅读全文</a> </div> <footer class="post-footer clearfix"> <div class="pull-left tag-list"> <i class="fa fa-folder-open-o"></i> ' + tagblock + '</div><div class="pull-right share"> </div> </footer> </article>'
        }
    }else{
        if(fig!=null) {
            return '<article id=' + id + ' class="post"> <div class="post-head"><h1 class="post-title">' +
                '<a href="#"></a>' + title + '</h1> <div class="post-meta"><time class="post-date">发表于' + createTime + '</time> ' +
                '</div> </div><div class="featured-media"> <a href="#"><img src="'+field+''+path1+''+fig+'" ></a>' +
                ' </div><div class="post-content"> <p>' + describe + '</p> </div> <div class="post-permalink"> ' +
                '<a href="../blog-detailness/blog-detail.html?id=' + id + '" class="btn btn-default">阅读全文</a> </div> ' +
                '<footer class="post-footer clearfix"> <div class="pull-left tag-list"> <i class="fa fa-folder-open-o">' +
                '</i> ' + tagblock + '</div><div class="pull-right share"> </div> </footer> </article>'
        }else{
            return '<article id=' + id + ' class="post"> <div class="post-head"><h1 class="post-title"><a href="#"></a>' + title + '</h1> <div class="post-meta"><time class="post-date">发表于' + createTime + '</time> </div> </div><div class="post-content"> <p>' + describe + '</p> </div> <div class="post-permalink"> <a href="../blog-detailness/blog-detail.html?id=' + id + '" class="btn btn-default">阅读全文</a> </div> <footer class="post-footer clearfix"> <div class="pull-left tag-list"> <i class="fa fa-folder-open-o"></i> ' + tagblock + '</div><div class="pull-right share"> </div> </footer> </article>'
        }
    }
    }

function getLocalTime(nS) {
    var date = new Date(nS);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    return Y+M+D;
}

function noLeftRightPage(currentPageNum,allPageNum){
    return '<nav class="pagination" role="navigation"> <span class="page-number">第'+currentPageNum+'页 &frasl; 共 '+allPageNum+' 页</span></nav>';
}
function noLeftPage(currentPageNum,allPageNum){
    var next = eval(currentPageNum)+1;
    return '<nav class="pagination" role="navigation"> <span class="page-number">第'+currentPageNum+'页 &frasl; 共 '+allPageNum+' 页</span> <a class="older-posts" href="../../pages/blog/blog.html?page='+next+'"><i class="fa fa-angle-right"></i></a> </nav>';
}

function noRightPage(currentPageNum,allPageNum){
    var pre = eval(currentPageNum)-1;
    return '<nav class="pagination" role="navigation"> <a class="newer-posts" href="../../pages/blog/blog.html?page='+pre+'"><i class="fa fa-angle-left"></i></a> <span class="page-number">第 '+currentPageNum+' 页 &frasl; 共 '+allPageNum+'页</span> </nav>';
}

function bothPage(currentPageNum,allPageNum){
    var pre = eval(currentPageNum)-1;
    var next = eval(currentPageNum)+1;
    return '<nav class="pagination" role="navigation"> <a class="newer-posts" href="../../pages/blog/blog.html?page='+pre+'"><i class="fa fa-angle-left"></i></a> <span class="page-number">第'+currentPageNum+' 页 &frasl; 共 '+allPageNum+'页</span> <a class="older-posts" href="../../pages/blog/blog.html?page='+next+'"><i class="fa fa-angle-right"></i></a> </nav>';
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("=");
        var strvalue = strs[1];
    }
    return strvalue;
}
function getAllPageNum(){
    var blogNum="";
    jQuery.ajax({
        type:"GET",
        url:"../../blog/num.do",
        data:"",
        dataType:"json",
        async : false,
        success:function(data){
           pageNum = data;
        }
    });
    return pageNum;
}

/*===========================
 2. Initialization
 ==========================*/
$(document).ready(function(){
    themeApp.init();
});