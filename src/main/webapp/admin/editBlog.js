var editBlog = function () {

    return {
        //main function to initiate the module
        init: function () {
            //判断博客是不是要修改的，如果是需要修改的，则填充markdwn文件到页面，否则继续向下执行
            var id = GetRequest();
            console.log("name"+id);
            if(id!=" "){
                var url = "../blog/blog-detail/"+id+".do";
                jQuery.ajax({
                    type:"GET",
                    url:url,
                    data:"",
                    dataType:"json",
                    success:function(data) {
                        $("#content-markdown").html(data._content_markdown);
                        console.log("title"+data._title);
                        $("input[name='add-blogID']").val(data._id);
                        $("input[name='add-blogName']").val(data._title);
                        $("input[name='add-blogDes']").val(data._describe);
                        //$("#add-blogName").html(data._title);
                        var tags ="";
                        var taglist = data._tags;
                        if(taglist.length==1){
                            tags = taglist[0]._name;
                            $("input[name='add-blogTag']").val(tags);
                        }else{
                            for (var i = 0; i < taglist.length-1;i++){
                                tags += taglist[i]._name+"#";
                            }
                            tags = tags+taglist[taglist.length-1]._name;
                            $("input[name='add-blogTag']").val(tags);
                        }

                    }
                })
            }

                testEditor = editormd("test-editormd", {
                    width: "100%",
                    height: 740,
                    syncScrolling: "single",
                    //你的lib目录的路径，我这边用JSP做测试的
                    path: "plugins/makedown/lib/",
                    //这个配置在simple.html中并没有，但是为了能够提交表单，使用这个配置可以让构造出来的HTML代码直接在第二个隐藏的textarea域中，方便post提交表单。
                    imageUpload: true,
                    imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                    imageUploadURL: "blog/uploadBlogFile",
                    theme: "dark",
                    editorTheme: "pastel-on-dark",
                    previewTheme: "dark",
                    saveHTMLToTextarea: true
                });

            $("#save-blog").bind('click', function() {
                alert(testEditor.getHTML());
                var html_concent = testEditor.getHTML();
                var add_blog_html = document.getElementById('add-blog-html');
                var markdown_concent = testEditor.getMarkdown();
                var add_blog_markdown = document.getElementById('add-blog-markdown');
                add_blog_html.value = html_concent;
                add_blog_markdown.value = markdown_concent;
            });

            function getLocalTime(nS) {
                var date = new Date(nS);
                Y = date.getFullYear() + '-';
                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                D = date.getDay() + ' ';
                H = date.getHours() + ':';
                M = date.getMinutes()
                return Y + M + D + H + M;
            }
            $("button[id='addImg']").on('click', function () {
                addImg();
            });

            $("button[id='addBlog']").on('click', function () {
                //如果博客是修改的，则进行更新操作，否则进行插入数据库的操作
                //var blogId = GetRequest();
                var blogId = document.getElementById('add-blogID').value;
                console.log("--------"+blogId);
                if(blogId == ''){
                    console.log("#######"+blogId);
                    if (!$("#add-blog-form").valid()) return;
                    addBlog();
                }else {
                    console.log("--------"+blogId);
                    updateBlog();
                }
            });
            function GetRequest() {
                var url = location.search; //获取url中"?"符后的字串
                if (url.indexOf("?") != -1) {
                    var str = url.substr(1);
                    var strs = str.split("=");
                    return strs[1];
                }else{
                    return url;
                }
            }

            function updateBlog(){
                $("#add-blog-form").ajaxSubmit({
                    dataType: 'json',
                    url: '../blog/uploadUpdateBlog.do',
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success: function (data) {
                        $("#add-responsive").modal('hide');
                        var r;
                        console.log("su" + data)
                        if (data == "success") {

                            r = "添加成功！";
                        } else {
                            r = "添加失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    },
                    error: function (data) {
                        $("#add-responsive").modal('hide');
                        var r;
                        console.log("fai" + data)
                        if (data.status == 200) {
                            r = "添加成功！";
                        } else {
                            r = "添加失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    }
                });
            }
            function addBlog() {

                $("#add-blog-form").ajaxSubmit({
                    dataType: 'json',
                    url: '../blog/uploadEditBlog.do',
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success: function (data) {
                        $("#add-responsive").modal('hide');
                        var r;
                        console.log("su" + data)
                        if (data == "success") {

                            r = "添加成功！";
                        } else {
                            r = "添加失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    },
                    error: function (data) {
                        $("#add-responsive").modal('hide');
                        var r;
                        console.log("fai" + data)
                        if (data.status == 200) {
                            r = "添加成功！";
                        } else {
                            r = "添加失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    }
                });
            }
            function addImg(){
                $("#add-img-form").ajaxSubmit({
                    dataType: 'text',
                    url: '../blog/uploadImg.do',
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success: function (data) {
                        $("#add-responsive-img").modal('hide');
                        var r;
                        console.log("su" + data)
                        if (data != "fail") {
                            r_result = "添加成功"
                            r_data = data;
                        } else {
                            r_result = "添加失败！";
                        }
                        $("h2#all-result").html(r_result);
                        $("h5#all-result-detail").html(r_data);
                        $("#result-responsive").modal('show');
                    },
                    error: function (data) {
                        $("#add-responsive-img").modal('hide');
                        var r;
                        console.log("fail" + data)
                        if (data.status == 200) {
                            r_result = "添加成功"
                            r_data = data;
                        } else {
                            r_result = "添加失败！";
                        }

                        $("h2#all-result").html(r_result);
                        $("h5#all-result-detail").html(r_data);
                        $("#result-responsive").modal('show');
                    }
                });
            }
            $('#add-blog-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    "add-blogTag": {
                        required: true,
                        remote: {
                            type: "GET",
                            dataType: "json",
                            url: "../blog/checkTagisExist.do",
                            data: {
                                blogTag: function () {
                                    return $("input[name='add-blogTag']").val();
                                }
                            }
                        }
                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
                    "add-blogTag": {
                        required: "填写存在的标签",
                        remote: "该标签不存在"
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

        }
    }
}();


