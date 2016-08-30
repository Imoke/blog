var manageCluster = function () {

    return {
        init: function () {
            showRow();
            function showRow(){
                    $.ajax({
                        type:"POST",
                        dataType:"json",
                        url:'managerHost/showAllClusterList.action',
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
                                var ClusterList = data.p_showClusterList;
                                for(var i = 0; i < ClusterList.length;i++){
                                    console.log(ClusterList[i]);
                                    oTable.fnAddData([ClusterList[i].colony_id,
                                        ClusterList[i].colony_name,
                                        ClusterList[i].colony_server,
                                        ClusterList[i].create_time,
                                        '<a href="javascript:;" class="edit">编辑</a> / '+
                                        '<a href="javascript:;" class="look">查看</a> / '+
                                        '<a href="javascript:;" class="delete">删除</a>'
                                    ]);
                                }
                                console.log("++");
                                console.log(oTable.fnGetData.length);
                            }
                    });
                }

            function getColonyInfoById(colonyId){
                var r;
                $.ajax({
                        type:"POST",
                        dataType:'json',
                        async:false,
                        url:'managerHost/showClusterInfoById.action',
                        data:{q_colony_id:colonyId},
                        success:function(data){
                            r = data;
                        },
                        error:function(){
                          console.log("error request");
                        }
                    });
                console.log(r);
                return r;
            }

            //编辑时加载主机下拉框
            function getHostsByClusterId(ClusterId, masterIp){
                $.ajax({
                        type:"POST",
                        dataType:'json',
                        url:'/main/getHostListByColonyId/',
                        data:{colonyId:ClusterId},
                        success:function(data){
                            console.log(data);
                            var select = $("select[name='edit-colonyHost']");
                            select.empty();
                            if(data[0].ip == 0){
                                select.append("<option value=''></option>");
                            }else{
                                select.append("<option value=''></option>");
                                for(var i = 0;i<data.length; i++){
                                    if(data[i].ip == masterIp){
                                        select.append("<option value='"+data[i].ip+"' selected>"+data[i].ip+"</option>");
                                    }else{
                                        select.append("<option value='"+data[i].ip+"'>"+data[i].ip+"</option>");
                                    }
                                }
                            }
                        },
                        error:function(){
                          console.log("error request");
                        }
                    });
            }

            $("button[id='addCluster']").on('click', function () {

                if(!$("#add-cluster-form").valid()) return;

                var colonyId = $("input[name='add-colonyId']").val();
                var colonyName = $("input[name='add-colonyName']").val();
                var colonyHost = $("select[name='add-colonyHost']").val();
                var colonyComment = $("textarea[name='add-colonyComment']").val();
                addColony(colonyId, colonyName, colonyHost, colonyComment);
            });

            $("button[id='editCluster']").on('click', function () {

                if(!$("#edit-cluster-form").valid()) return;

                var colonyId = $("input[name='edit-colonyId']").val();
                var colonyName = $("input[name='edit-colonyName']").val();
                var colonyHost = $("select[name='edit-colonyHost']").val();
                var colonyComment = $("textarea[name='edit-colonyComment']").val();
                editColony(colonyId, colonyName, colonyHost, colonyComment);
            });

            function addColony(colonyId, colonyName, colonyHost, colonyComment){
                console.log(colonyId);
                $.ajax({
                        type:"POST",
                        dataType:'json',
                        url:'managerHost/addClusterInfo.action',
                        data:{q_colony_id:colonyId, q_colony_name:colonyName, q_colony_server:colonyHost, q_colony_comment:colonyComment},
                        success:function(data){
                            $("#add-responsive").modal('hide');
                            var r ;
                            if(data.result == 1){
                                //alert("success");
                                r = "添加成功！";
                                showRow();
                            }else if(data.result == 0){
                                r = "ID重复！";
                            }else{
                                r = "添加成功！";
                            }
                            $("h2#all-result").html(r);
                            $("#result-responsive").modal('show');
                        },
                        error:function(){
                          console.log("error request");
                        }
                    });
            }

            function editColony(colonyId, colonyName, colonyHost, colonyComment){
                $.ajax({
                        type:"POST",
                        dataType:'json',
                        url:'managerHost/editClusterInfo.action',
                        data:{q_colony_id:colonyId, q_colony_name:colonyName, q_colony_server:colonyHost, q_colony_comment:colonyComment},
                        success:function(data){
                            $("#edit-responsive").modal('hide');
                            var r ;
                            if(data.result == 1){
                                r = "修改成功！";
                                showRow();
                            }else{
                                r = "修改成功！";
                            }
                            $("h2#all-result").html(r);
                            $("#result-responsive").modal('show');
                        },
                        error:function(){
                          console.log("error request");
                        }
                    });
            }

            function deleteColony(colonyId){
                var flag = true;
                $.ajax({
                        type:"POST",
                        dataType:'json',
                        async:false,
                        url:'managerHost/deleteCluster.action',
                        data:{q_colony_id:colonyId},
                        success:function(data){
                            //$("#edit-responsive").modal('hide');
                            var r ;
                            if(data.result == 1){
                                //alert("success");
                                r = "删除成功！";
                                flag = true;
                                //oTable.fnDeleteRow(nRow);
                                //showRow();
                            }else{
                                r = "添加成功！";
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
                var colonyId = aData[0];
                var flag = deleteColony(colonyId);
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

            $('#sample_editable_1 a.look').live('click', function (e) {
                e.preventDefault();
                $("#look-responsive").modal('show');

                ///* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var colonyInfo = getColonyInfoById(aData[0])
                $("#look_colonyId").html(aData[0]);
                $("#look_colonyName").html(aData[1]);
                $("#look_colonyHostIp").html(aData[2]);
                $("#look_colonyCreateTime").html(aData[3]);
                $("#look_colonyModifyTime").html(colonyInfo.colonyModifyTime);
                $("#look_colonyComment").html(colonyInfo.colonyComment);
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
                var clusterInfo = getColonyInfoById(aData[0]);
                console.log("____________");
                console.log(clusterInfo);
                $("input[name='edit-colonyId']").val(aData[0]);
                $("input[name='edit-colonyName']").val(aData[1]);
                $("textarea[name='edit-colonyComment']").html(clusterInfo.colonyComment);
                getHostsByClusterId(aData[0],aData[2]);
            });



            $('#add-cluster-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    "add-colonyId": {
                            isUpperCase: true,
                            maxlength:1,
                            required:true,
                            remote:{
                                type:"GET",
                                dataType:"json",
                                url:"/manager/checkColonyId/",
                                data:{
                                    colonyId: function () {
                                        return $("input[name='add-colonyId']").val();
                                    }
                                },
                                dataFilter: function (data) {
                                    return data != 1;
                                }
                            }
                        },

	                "add-colonyName": {
	                    required: true,
                        maxlength:20,
                        isLetterAndDigit:true
	                }
                },
                messages: { // custom messages for radio buttons and checkboxes
					"add-colonyId": {
                        maxlength:jQuery.format("单个大写字母"),
                        required: "id不能为空",
						remote:"该id已存在"
	                },
	                "add-colonyName": {
	                    required: "集群名不能为空",
                        maxlength:jQuery.format("长度不能超过{0}")
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
            $('#edit-cluster-form').validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
	                "edit-colonyName": {
	                    required: true,
                        maxlength:20,
                        isLetterAndDigit:true
	                }
                },
                messages: { // custom messages for radio buttons and checkboxes
	                "edit-colonyName": {
	                    required: "集群名不能为空",
                        maxlength:jQuery.format("长度不能超过{0}")
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


