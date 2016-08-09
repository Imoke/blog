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
            data:"",
            dataType:"json",
            success:function(data){
                console.log(data);
                blocks = aBlogBlock(data._id,data._title,getLocalTime(data._create_at),data._content);
                $("#block").append(blocks);
            }
        });
    },
    init: function() {
        themeApp.getblog();
        themeApp.backToTop();
    }

}

function aBlogBlock(id,title,createTime,content){
    return '<article id='+id+' class="post"> <div class="post-head"><h1 class="post-title"><a href="#"></a>'+title+'</h1> <div class="post-meta"><span class="author">本篇系原创，转载请注明出处</span> <time class="post-date">'+createTime+'</time> </div> </div><div class="post-content"> <p>'+content+'</p></div> </article>'
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

/*===========================
 2. Initialization
 ==========================*/
$(document).ready(function(){
    themeApp.init();
});