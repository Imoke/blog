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
    showBlog:function(){
        var name = GetRequest();
        var url = "../../blog/blog-class/"+name+".do";
        jQuery.ajax({
                type:"GET",
                url:url,
                data:"",
                dataType:"json",
                success:function(data) {
                    if (data == "") {
                        var titleBlock = aTitleBlock(name, 0);
                        $("#title_block").append(titleBlock);
                    }
                    else {
                        var post_num = data.length;
                        var tags_num = data[0]._tags.length;
                        var blogBlocks = '';
                        for (var m = 0; m < tags_num; m++) {
                            var tag = data[0]._tags[m];
                            if (name == tag._name_eng) {
                                var post_name = tag._name;
                                break;
                            }
                        }

                        var titleBlocks = aTitleBlock(post_name, post_num);
                        $("#title_block").append(titleBlocks);
                        for (var i = 0; i < post_num; i++) {
                            blogBlocks += aBlogBlock(data[i]._id, data[i]._title, getLocalTime(data[i]._create_at), data[i]._describe);
                        }
                        $("#blog_block").append(blogBlocks);
                    }
                }
    })
    },
    init: function() {
        themeApp.showBlog();
        themeApp.backToTop();
    }

}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("=");
    }
    return strs[1];
}

function getLocalTime(nS) {
    var date = new Date(nS);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    return Y+M+D;
}
function aTitleBlock(titleName,num){
    return '<div class="cover tag-cover"><h3 class="tag-name">标签：'+titleName+' </h3> <div class="post-count">共 '+num+' 篇文章 </div> </div>';
}
function aBlogBlock(id,title,createTime,describe){
    return '<article id='+id+' class="post"> <div class="post-head"><h1 class="post-title"><a href="#"></a>'+title+'</h1> <div class="post-meta"><time class="post-date">发表于'+createTime+'</time> </div> </div><div class="post-content"> <p>'+describe+'</p></div><div class="post-permalink"> <a href="../blog-detailness/blog-detail.html?id='+id+'" class="btn btn-default">阅读全文</a> </div>  </article>'
}

/*===========================
 2. Initialization
 ==========================*/
$(document).ready(function(){
    themeApp.init();
});