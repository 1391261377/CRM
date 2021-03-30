<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css"
          rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css"
          type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/bs_pagination/en.js"></script>


    <script type="text/javascript">

        $(function () {
            //添加事件 打开添加操作模态窗口
            $("#addBtn").click(function () {

                //日期格式
                $(".time").datetimepicker({
                    minView: "month",
                    language: "zh-CN",
                    format: "yyyy-mm-dd",
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });


                $.ajax({
                    url: "${pageContext.request.contextPath}/activity/gitUserList",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        var html = "<option></option>";
                        $.each(data, function (i, n) {
                            html += "<option value='" + n.id + "'>" + n.name + "</option>"
                        })

                        $("#create-owner").html(html);

                        var id = "${sessionScope.user.id}";
                        $("#create-owner").val(id);

                        $("#createActivityModal").modal("show");
                    }
                })
            })

            //为保存按钮添加事件
            $("#saveBtn").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/activity/save",
                    data: {

                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())

                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data) {


                            //刷新市场活动信息列表
                            $("#activityAddForm")[0].reset();
                            //刷新 信息列表
                            //$("#activityPage").bs_pagination('getOption','rowsPerPage') 维持每页的展示数 用户设置多少就是多少不变的
                            pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                            //关闭模态窗口
                            $("#createActivityModal").modal("hide");
                        } else {
                            alert("添加失败")
                        }
                    }

                })
            });


            //页面加载完毕触发方法
            pageList(1, 2);

            /*
                pageNo  页码
                pageSize  每页展示的数据
             */


            //为查询绑定事件

            $("#searchBtn").click(function () {

                /**
                 *   将信息保存到隐藏域中
                 */
                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));
                pageList(1, 2);
            })


            //为全选复选框增加事件
            $("#qx").click(function () {
                $("input[name=xz]").prop("checked", this.checked)

            })

            $("#activityBody").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length)
            })


            //为删除绑定事件
            $("#deleteBtn").click(function () {
                var $xz = $("input[name=xz]:checked");
                if ($xz.length == 0) {
                    alert("请选择需要删除的记录")
                } else {
                    //给用户弹框
                    if (confirm("确定删除所选中的记录")) {

                        var param = "";
                        for (var i = 0; i < $xz.length; i++) {
                            param += "id=" + $($xz[i]).val();
                            if (i < $xz.length - 1) {
                                param += "&";
                            }
                        }

                        $.ajax({
                            url: "${pageContext.request.contextPath}/activity/delete",
                            data: param,
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                if (data) {
                                    //删除成功 刷新页面 维持到第一页 维持每页展示的页数
                                    pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                                } else {
                                    alert("删除市场活动失败")
                                }
                            }

                        })

                    }


                }


            })

            //为修改按钮绑定事件
            //添加事件 打开添加操作模态窗口
            $("#editBtn").click(function () {

                var $xz = $("input[name=xz]:checked");
                if ($xz.length == 0) {
                    alert("请选择需要修改的记录")
                } else if ($xz.length > 1) {
                    alert("自能选择一条记录进行修改")
                } else {
                    //肯定只有一条
                    var id = $xz.val();

                    $.ajax({
                        url: "${pageContext.request.contextPath}/activity/getUserListAndActivity",
                        data: {
                            "id": id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            var html = "<option></option>";
                            $.each(data.uList, function (i, n) {
                                html += "<option value='" + n.id + "'>" + n.name + "</option>";

                            })


                            $("#edit-owner").html(html);

                            $("#edit-id").val(data.a.id);
                            $("#edit-name").val(data.a.name);
                            $("#edit-owner").val(data.a.owner);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-description").val(data.a.description);

                            //打开修改操作的模态窗口
                            $("#editActivityModal").modal("show");
                        }
                    })
                }
            })

            /**
             * 在实际开发 一般 先做添加 再修改
             */
            //为更新按钮绑定事件
            $("#updateBtn").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/activity/update",
                    data: {
                        "id": $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startDate").val()),
                        "endDate": $.trim($("#edit-endDate").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-description").val())

                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data) {

                            //刷新市场活动信息列表  维持到当前页
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


                            //关闭模态窗口
                            $("#editActivityModal").modal("hide");
                        } else {
                            alert("修改失败")
                        }
                    }

                })

            })

        });

        //分页功能
        function pageList(pageNo, pageSize) {
            //将全选的复选框 清空
            $("#qx").prop("checked", false);

            //在查询前 将隐藏信息中d的信息取出来
            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));


            $.ajax({
                url: "${pageContext.request.contextPath}/activity/pageList",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,

                    "name": $.trim($("#search-name").val()),
                    "owner": $.trim($("#search-owner").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val()),


                },
                type: "get",
                dataType: "json",
                success: function (data) {


                    var html = "";
                    $.each(data.dataList, function (i, n) {
                        html += '<tr class="active" id="activityBody">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'${pageContext.request.contextPath}/activity/detail?id=' + n.id + '\';">' + n.name + '</a>';
                        html += '</td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += ' </tr>';

                    })

                    $("#activityBody").html(html);

                    //计算总页数
                    var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;

                    //数据处理完毕后，结合分页插件 对前端展示分页相关的信息

                    $('#activityPage').bs_pagination({
                        currentPage: pageNo,//当前的请求页面。
                        rowsPerPage: pageSize,//一共多少页。
                        maxRowsPerPage: 20,//每页最多显示记录条数
                        totalPages: totalPages, //总页数
                        totalRows: data.total, //总记录条数

                        visiblePageLinks: 3, //显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,
                        onChangePage: function (event, data) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });

                }

            })

        }


    </script>
    <title></title>
</head>
<body>


<input type="hidden" id="hidden-name">
<input type="hidden" id="hidden-owner">
<input type="hidden" id="hidden-startDate">
<input type="hidden" id="hidden-endDate">

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="activityAddForm">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner"></select>
                        </div>
                        <label class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate">
                        </div>
                        <label class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>
                </form>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id">
                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner"></select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name" value="发传单">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startDate" value="2020-10-10">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endDate" value="2020-10-20">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <!--
                                    关于textarea 文本域
                                        一定要以标签对的形式来呈现 不要有空格
                                        textarea 虽然是以标签对的形式来呈现的d 但是它也是属于表单元素范畴
                                            对于  textarea的取值和赋值 统一使用val() 方法
                            -->
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="searchBtn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">

            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">
                <%--  <tr class="active" id="activityBody">
                    --<td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='${pageContext.request.contextPath}/static/workbench/activity/detail.jsp';">发传单</a>
                    </td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                  </tr>

                    <tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='detail.html';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr> --%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage">

            </div>
        </div>

    </div>

</div>
</body>
</html>