<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <link href="${pageContext.request.contextPath}/static/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css"
          rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <title>登录页面</title>
    <script type="text/javascript">

        $(function () {

            if(window.top!=window){
                window.top.location=window.location;
            }
            //页面加载完毕 将用户文本框清空
            $("#loginAct").focus();
            //页面加载完毕 让用户的文本框自动获得焦点
            $("#loginAct").val("");

            //为登录绑定事件
            $("#submitBtn").click(function () {
                login();
            })

            //为窗口绑定键盘敲击事件
            $(window).keydown(function (even) {
                if (even.keyCode === 13) {
                    login();
                }
            })

        })


        function login() {
            // 验证账号密码不为空
            var loginAct = $.trim($("#loginAct").val());
            var loginPwd = $.trim($("#loginPwd").val());

            if (loginAct == "" || loginPwd == "") {
                $("#msg").html("账号密码不能为空")

            }

            $.ajax({
                url: "${pageContext.request.contextPath}/user/selectLogin",
                data: {
                    "loginAct": loginAct,
                    "loginPwd": loginPwd
                },
                type: "post",
                dataType: "json",
                success: function (data) {

                    if (data){
                        window.location.href="${pageContext.request.contextPath}/static/workbench/index.jsp";
                    }else {
                        $("#msg").html("用户名或密码错误!");
                    }
                }

            })

        }
    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="${pageContext.request.contextPath}/static/image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" placeholder="用户名" id="loginAct">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" placeholder="密码" id="loginPwd">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color: red"></span>

                </div>
                <button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>