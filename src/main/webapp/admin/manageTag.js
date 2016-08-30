/**
 * Created by LWang on 2016/7/23.
 */

var manageTags = function () {

    return {
        init: function () {
            showRow();
            function showRow(){
                $.ajax({
                    type:"POST",
                    dataType:"json",
                    url:'../tags/all.do',
                    error:
                        function(){
                            alert('fail');
                        },
                    success:
                        function(data){
                            console.log("--");
                            console.log(oTable);
                            console.log(oTable.fnGetData.length);
                            oTable.fnClearTable();
                            //if(oTable)
                            var tagsList = data;
                            for(var i = 0; i < tagsList.length;i++){
                                console.log(tagsList[i]);
                                oTable.fnAddData([tagsList[i]._id,
                                    tagsList[i]._name,
                                    tagsList[i]._name_eng,
                                    getLocalTime(tagsList[i]._creat_at),
                                    getLocalTime(tagsList[i]._update_at),
                                    tagsList[i]._is_exist,
                                    '<a href="javascript:;" class="edit">编辑</a> / '+
                                    '<a href="javascript:;" class="delete">删除</a>'
                                ]);
                            }
                            console.log("++");
                            console.log(oTable.fnGetData.length);
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

            $("button[id='addTag']").on('click', function () {

                if(!$("#add-tag-form").valid()) return;

                var tagName = $("input[name='add-tagName']").val();
                var tagEngName = $("input[name='add-tagEngName']").val();
                addTag(tagName, tagEngName);
            });

            $("button[id='editTag']").on('click', function () {

                if(!$("#edit-tag-form").valid()) return;
                var tagId = $("input[name='edit-tagId']").val();
                var tagName = $("input[name='edit-tagName']").val();
                var tagEngName = $("input[name='edit-tagEngName']").val();
                editTag(tagId,tagName, tagEngName);
            });

            function addTag(tagName, tagEngName){
                console.log(tagName);
                $.ajax({
                    type:"POST",
                    dataType:'json',
                    url:'../tags/manage/addTagInfo.do',
                    data:{tagName:tagName, tagEngName:tagEngName},
                    success:function(data){
                        $("#add-responsive").modal('hide');
                        var r ;
                        if(data== true){
                            r = "添加成功！";
                            showRow();
                        }else {
                            r = "添加失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    },
                    error:function(){
                        console.log("error request");
                    }
                });
            }

            function editTag(tagId,tagName, tagEngName){
                $.ajax({
                    type:"POST",
                    dataType:'json',
                    url:'../tags/manage/editTagInfo.do',
                    data:{tagId:tagId,tagName:tagName, tagEngName:tagEngName},
                    success:function(data){
                        $("#edit-responsive").modal('hide');
                        var r ;
                        if(data==true){
                            r = "修改成功！";
                            showRow();
                        }else{
                            r = "修改失败！";
                        }
                        $("h2#all-result").html(r);
                        $("#result-responsive").modal('show');
                    },
                    error:function(){
                        console.log("error request");
                    }
                });
            }

            function deleteTag(tagId){
                var flag = true;
                $.ajax({
                    type:"POST",
                    dataType:'json',
                    async:false,
                    url:'../tags/manage/delTagInfo.do',
                    data:{tagId:tagId},
                    success:function(data){
                        //$("#edit-responsive").modal('hide');
                        var r ;
                        if(data==true){
                            //alert("success");
                            r = "删除成功！";
                            flag = true;
                            //oTable.fnDeleteRow(nRow);
                            //showRow();
                        }else{
                            r = "删除失败！";
                            flag = false;
                            //alert("failed with no reason");
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

                if (confirm("Are you sure to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var TagId = aData[0];
                var flag = deleteTag(TagId);
                if(flag){
                    oTable.fnDeleteRow(nRow);
                }
                //alert("Deleted! Do not forget to do some ajax to sync with backend :)");
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

                //alert(0)
                e.preventDefault();
                $("#edit-responsive").modal('show');

                ///* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                console.log("nRow = " + nRow);
                console.log("aData = " + aData);
                console.log("____________");
                $("input[name='edit-tagId']").val(aData[0]);
                $("input[name='edit-tagName']").val(aData[1]);
                $("input[name='edit-tagEngName']").val(aData[2]);
            });



            $('#add-tag-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    "add-tagName": {
                        required: true,
                        maxlength: 10,
                        remote: {
                            type: "GET",
                            dataType: "json",
                            url: "../tags/manage/checkTagName.do",
                            data: {
                                tagName: function () {
                                    return $("input[name='add-tagName']").val();
                                }
                            }
                        }
                    },

                    "add-tagEngName": {
                        required: true,
                        maxlength: 20,
                        remote: {
                            type: "GET",
                            dataType: "json",
                            url: "../tags/manage/checkTagEngName.do",
                            data: {
                                tagEngName: function () {
                                    return $("input[name='add-tagEngName']").val();
                                }
                            }
                        }
                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
                    "add-tagName": {
                        required: "标签名不能为空",
                        maxlength:jQuery.format("长度不能超过{0}"),
                        remote:"该标签名已存在"
                    },
                    "add-tagEngName": {
                        required: "标签别名不能为空",
                        maxlength:jQuery.format("长度不能超过{0}"),
                        remote:"该标签别名已存在"
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
            $('#edit-tag-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    "edit-tagName": {
                        maxlength: 10,
                        required: true,
                        remote: {
                            type: "GET",
                            dataType: "json",
                            url: "../tags/manage/checkTagName.do",
                            data: {
                                tagName: function () {
                                    return $("input[name='edit-tagName']").val();
                                }
                            }
                        }
                    },

                    "edit-tagEngName": {
                        required: true,
                        maxlength: 20,
                        remote: {
                            type: "GET",
                            dataType: "json",
                            url: "../tags/manage/checkTagEngName.do",
                            data: {
                                tagEngName: function () {
                                    return $("input[name='edit-tagEngName']").val();
                                }
                            }
                        }
                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
                    "edit-tagName": {
                        required: "标签名不能为空",
                        maxlength:jQuery.format("长度不能超过{0}"),
                        remote:"该标签名已存在"
                    },
                    "edit-tagEngName": {
                        required: "标签别名不能为空",
                        maxlength:jQuery.format("长度不能超过{0}"),
                        remote:"该标签别名已存在"
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
    };

}();


