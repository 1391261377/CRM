<%--
  Created by IntelliJ IDEA.
  User: 胡
  Date: 2021/2/10
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/static/ECharts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.1-min.js"></script>

    <title>Echarts</title>

    <script type="text/javascript">

        $(function () {




            //页面加载完毕后 绘制统计图表
            getCharts()




        })


        function getCharts() {

            // 内容要的是一个json 数据

            $.ajax({
                url: "${pageContext.request.contextPath}/transaction/getCharts",
                type: "get",
                dataType: "json",
                success: function (data) {


                    //   基于dom 准备好dom  初始化echarts实列
                    var myChart=echarts.init(document.getElementById('main'));


                    //指定图标的配置项和数据
                    var  option = {
                        title: {
                            text: '交易漏斗图',
                            subtext: '统计交易阶段数量的漏斗图'
                        },
                        // tooltip: {
                        //     trigger: 'item',
                        //     formatter: "{a} <br/>{b} : {c}%"
                        // },
                        // toolbox: {
                        //     feature: {
                        //         dataView: {readOnly: false},
                        //         restore: {},
                        //         saveAsImage: {}
                        //     }
                        // },
                        // legend: {
                        //     data: ['展现','点击','访问','咨询','订单']
                        // },

                        series: [
                            {
                                name:'漏斗图',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                //x2: 80,
                                bottom: 60,
                                width: '80%',
                                // height: {totalHeight} - y - y2,
                                min: 0,
                                max: 100,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                data:  data.dataList

                                //     [
                                //     {value: 60, name: '访问'},
                                //     {value: 40, name: '咨询'},
                                //     {value: 20, name: '订单'},
                                //     {value: 80, name: '点击'},
                                //     {value: 100, name: '展现'}
                                // ]
                            }
                        ]
                    };
                    myChart.setOption(option);


                }

            })










        }
    </script>
</head>
<body>

<%--
     Echarts 统计图表
        不支持jQuery对象 只支持原生dom 对象
--%>

<div id="main" style="width: 600px;height:400px ;"></div>
</body>
</html>
