var manageHost = function () {

    return {
        //main function to initiate the module
        init: function () {
            showRow();

            function showRow(){
                    $.ajax({
                        type:"POST",
                        dataType:"json",
                        url:'../blog/all_blog.do',
                        error:
                            function(){
                            alert('fail');
                        },
                        success:
                            function(data){
                                oTable.fnClearTable();
                                var obj = eval(data);
                                for(var i = 0; i < obj.length;i++){
                                    var item = obj[i];
                                    for(var j =0; item.length; j++) {
                                        var tags = "";
                                        var taglist = item[j]._tags;
                                        if(taglist!=null) {
                                            for (var m = 0; m < taglist.length; m++) {
                                                tags += taglist[m]._name + " ";
                                            }
                                        }
                                        oTable.fnAddData(
                                            [   item[j]._id,
                                                item[j]._title,
                                                tags,
                                                getLocalTime(item[j]._create_at),
                                                getLocalTime(item[j]._update_at),
                                                item[j]._is_exist,
                                                '<a href="javascript:;" class="edit">编辑</a> / ' +
                                                '<a href="javascript:;" class="delete">删除</a>']);
                                    }
                                }
                            }
                    });
                }

            function getLocalTime(nS) {
                var date = new Date(nS);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDay() + ' ';
                H = date.getHours()+ ':';
                M = date.getMinutes()
                return Y+M+D+H+M;
            }
            $("button[id='addBlog']").on('click', function () {
                if(!$("#add-blog-form").valid()) return;
                addBlog();
            });

            $("button[id='editBlog']").on('click', function () {

               // if(!$("#edit-host-form").valid()) return;
                editBlog();
            });

            function addBlog(){
                $("#add-blog-form").ajaxSubmit({
                    dataType: 'json',
                    url: '../blog/upload.do',
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success:function(data){
                            $("#add-responsive").modal('hide');
                            var r ;
                            console.log("su"+data)
                            if(data=="success"){

                                r = "添加成功！";
                                showRow();
                            }else{
                                r = "添加失败！";
                            }
                            $("h2#all-result").html(r);
                            $("#result-responsive").modal('show');
                        },
                    error:function(data){
                        $("#add-responsive").modal('hide');
                        var r ;
                        console.log("fai"+data)
                        if(data.status==200){

                            r = "添加成功！";
                            showRow();
                        }else{
                            r = "添加失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                        }
                    });
            }

            function editBlog(){

                $("#edit-blog-form").ajaxSubmit({
                    dataType: 'json',
                    url: '../blog/editBlog.do',
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success:function(data){
                        $("#edit-responsive").modal('hide');
                        var r ;
                        console.log("su"+data)
                        if(data=="success"){
                            alert("success");
                            r = "编辑成功！";
                            //showRow();
                        }else{
                            r = "编辑失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    },
                    error:function(data){
                        $("#edit-responsive").modal('hide');
                        var r ;
                        console.log("fai"+data)
                        if(data.status==200){
                            alert("success");
                            r = "编辑成功！";
                            //showRow();
                        }else{
                            r = "编辑失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    }
                });
            }

            var flag = false; //删除标志

            function deleteBlog(blogId){
                console.log(blogId);
                $.ajax({
                        type:"POST",
                        dataType:'json',
                        async:false,
                        url:'../blog/deleteBlog.do',
                        data:{blogId:blogId},
                        success:function(data){
                            var r ;
                            if(data == true){
                                r = "删除成功！";flag = true;

                            }else{
                                r = "删除失败！";flag = false;
                            }
                            $("h2#all-result").html(r);
                            $("#result-responsive").modal('show');
                        },
                        error:function(){
                          console.log("error request");
                        }
                    });
                return flag;
            }


            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }
                oTable.fnDraw();
            }

            var oTable = $('#sample_editable_1').dataTable({
                "aLengthMenu": [
                    [5, 10, 15, 20, -1],
                    [5, 10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [1]
                    }
                ]
            });

            jQuery('#sample_editable_1_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#sample_editable_1_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            jQuery('#sample_editable_1_wrapper .dataTables_length select').select2({
                showSearchInput : false //hide search box with special css class
            }); // initialzie select2 dropdown

            var nEditing = null;

            $('#sample_editable_1 a.delete').live('click', function (e) {
                e.preventDefault();
                if (confirm("确定删除该博客吗 ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var blogId = aData[0];
                var flag = deleteBlog(blogId);
                if(flag){
                    oTable.fnDeleteRow(nRow);
                }
            });

            $('#sample_editable_1 a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#sample_editable_1 a.edit').live('click', function (e) {
                e.preventDefault();
                $("#edit-responsive").modal('show');
                ///* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                $("input[name='edit-blogID']").val(aData[0]);
                $("input[name='edit-blogName']").val(aData[1]);
                $("input[name='edit-blogTag']").val(aData[2]);
            });

            $('#add-blog-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    "add-blogTag": {
                            required:true,
                            remote:{
                                type:"GET",
                                dataType:"json",
                                url:"../blog/checkTagisExist.do",
                                data:{
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
						remote:"该标签不存在"
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
            $('#edit-blog-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {

                },
                messages: {

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

    };

}();


