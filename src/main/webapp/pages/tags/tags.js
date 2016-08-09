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
    showtags:function(){
        jQuery.ajax({
            type:"GET",
            url:"../../tags/all.do",
            data:"",
            dataType:"json",
            success:function(data){
                $("#tagBlock").append(tagsBlock(data));
            }
        });
    },
    showTimeLine:function(){
        jQuery.ajax({
            type:"GET",
            url:"../../blog/all_blog.do",
            data:"",
            dataType:"json",
            success:function(data){
                console.log(data);
                $("#timeLineBlock").append(allYearBlock(data));
            }
        });
    },
    init: function() {

        themeApp.backToTop();
        themeApp.showtags();
    }
}

function tagsBlock(tags) {
    var tag = "";
    if (tags.length>0) {
        for (var i = 0; i < tags.length; i++) {
            var aTag = tags[i];
            var blogNum = '';
            console.log("123"+aTag._blog_id);
            if(aTag._blog_id!=null){
                blogNum = aTag._blog_id.length;
            }else{
                blogNum = 0;
            }
            tag += '<a href="../blog-classification/blog-class.html?name=' + aTag._name_eng + '">' + aTag._name + '(' +blogNum + ')</a>';
        }
        return ' <h1>标签云</h1> <br/><br/> <div class="widget"> <div class="content tag-cloud"> ' + tag + ' </div> </div>';
    }
}

/*function allYearBlock(data){
    var yearNum = data.length;
    console.log("yearNum"+yearNum);
    var yearBlock = "";
    for(var i=0;i<yearNum;i++){
        yearBlock += getYearBlock(data[i]);
        console.log("data[i]"+data[i]);
    }
    return '<h1>时间轴</h1> <div class="demo"> <div class="history"> '+yearBlock+'</div> </div>';
}
function getYearBlock(theYear){
    var blogNum = theYear.length;
    console.log("blogNum"+blogNum);
    var year = "";
    var yearBlock = "";
    for(var i=0;i<blogNum;i++){
        var year = getYearTime(theYear[i]._create_at);
        yearBlock += getBlock(theYear[i]);
        console.log("theYear.posts[i]"+theYear[i]._create_at);
    }
    return '<div class="history-date"> <ul> <h2 class="first"> <a href="#nogo">'+year+'</a> </h2> '+yearBlock+'</ul> </div>';
}

function getBlock(blog){
    var time = getLocalTime(blog._create_at);
    return '<li class="green"> <h3>'+time+'</h3> <dl> <dt>'+blog._title+'</dt> </dl> </li>';
}*/
function getLocalTime(nS) {
    var date = new Date(nS);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    return Y+M+D;
}
function getYearTime(nS) {
    var date = new Date(nS);
    Y = date.getFullYear() ;
    return Y+"年";
}
/*===========================
 2. Initialization
 ==========================*/
$(document).ready(function(){
    themeApp.init();
});
