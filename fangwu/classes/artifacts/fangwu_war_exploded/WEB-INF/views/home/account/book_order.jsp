<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  <title>|幸福公寓房屋出租系统租赁</title>
  <meta name="Author" content="">
  <meta name="Keywords" content="幸福公寓房屋出租系统">
  <meta name="Description" content="幸福公寓房屋出租系统">
    <link rel="stylesheet" href="../../resources/home/css/index.css">
    <link rel="stylesheet" href="../../resources/home/css/order.css">
    <link rel="stylesheet" href="../../resources/home/css/jquery-ui.min.css">
    
        <script>
         
   </script>
</head>
<body>
<!--- 页头--->
<div id="c_header"></div>
<!----主体-->
<div id="section">
    <!--房屋信息-->
    <div class="hotel_inf_w">

        <div class="hotel_roominfobox">
            <a href="#"><img src="${roomType.photo }" alt=""/></a>
            <div class="info">
            <h2>${roomType.name }</h2>
           
            </div>
            <ul class="hotel_detail">
            <li><span>租赁数:</span>${roomType.bookNum }</li>
            <li><span>租金:</span>${roomType.price }</li>
            <li><span>床位数:</span>${roomType.bedNum }</li>
            <li><span>可住:</span>${roomType.liveNum }人</li>
            <li><span>其他:</span>${roomType.remark }</li>
            </ul>
        </div>
        <div class="jump">
         
            <a href="../index">更多房屋类型</a>
        </div>
    </div>
    <!--租赁信息-->
    <div class="book_info">
        <form id="order_info">
            <ul>
                <input type="hidden" name="rid" value=""/>
                <li>
                    <h3>租赁信息</h3>

<!--                     <div class="info_group"> -->
<!--                         <label>开始租赁时间</label><input type="text" name="arriveDate" id="arriveDate" class="datepicker"/> -->
<!-- 						<label>结束租赁时间</label><input type="text" name="leaveDate" id="leaveDate" class="datepicker"/> -->
<!-- 						<input type="button" value="确定" onclick="c()"> -->
<!-- <!-- 						<button onclick="c()">确定</button> --> 
<!-- <!-- 						相差天数:   <input id="day" /> --> 
<!--                     </div> -->
                    
                    <div class="info_group">
                        <label>租赁费用总计</label><span id="total" class="total">￥${roomType.price }元/月</span>
                        <input type="hidden" value="${roomType.price }" id="fangfei">
                        <input type="hidden" value="0"/>
                    </div>
                </li>
                <li>
                    <h3>用户信息</h3>

                    <div class="info_group">
                        <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label><input type="text" name="name" id="name" value="${account.name}"/><span class="msg"></span>
                    </div>
                    <div class="info_group">
                        <label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话</label><input type="text" maxlength="11" name="mobile" id="mobile" value="${account.mobile}"/><span class="msg"></span>
                    </div>
					<div class="info_group">
                        <label>身份证号</label><input type="text" name="idCard" id="idCard" value="${account.idCard}"/><span class="msg"></span>
                    </div>
                    <div class="info_group">
                        <label for="massage">留&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言</label>
                         <textarea id="remark" name="remark" style="width:200px;"></textarea>
                    </div>

                </li>
                <li>

                    <div id="btn_booking">确认租赁</div>

                </li>
            </ul>
        </form>
    </div>

    <div class="malog">
        <div class="message">
            <p class="msgs"></p>
            <p>您可以在 <a href="index#order">我的订单</a>查看详情</p>
            <p>系统会在<span class="num">5</span>秒后跳转会 <a href="../index">菜单列表</a></p>
        </div>
    </div>

</div>
<!----页底--->
<div id="c_footer"></div>
<script src="../../resources/home/js/jquery-1.11.3.js"></script>
<script src="../../resources/home/js/jquery-ui.min.js"></script>
</body>
<script>
function c(){
    var date1 = document.getElementById("arriveDate").value;
    var date2 = document.getElementById("leaveDate").value;
    var fangfei = document.getElementById("fangfei").value;
    
//     var total = document.getElementById("total").value;
    var result = (new Date(date1).getTime() - new Date(date2))/(24*3600*1000);
    var zongjia=fangfei*Math.abs(result);
//     alert(zongjia);
//     total.innerHTML=zongjia;
    $("#total").html(zongjia);
    document.getElementById("day").value = Math.abs(result);
     
    }
  $(function() {
    $(".datepicker").datepicker({"dateFormat":"yy-mm-dd"});
    $("#btn_booking").click(function(){
    	var arriveDate = $("#arriveDate").val();
    	var leaveDate = $("#leaveDate").val();
//     	if(arriveDate == '' || leaveDate == ''){
//     		alert('请选择时间!');
//     		return;
//     	}
    	var name = $("#name").val();
    	var price=$("#total").text();
    	var yajin=$("#yajin").text();
    	
    	if(name == ''){
    		$("#name").next("span.msg").text('请填写租赁人!');
    		return;
    	}
    	$("#name").next("span.msg").text('');
    	var mobile = $("#mobile").val();
    	if(mobile == ''){
    		$("#mobile").next("span.msg").text('请填写手机号!');
    		return;
    	}
    	$("#mobile").next("span.msg").text('');
    	var idCard = $("#idCard").val();
    	if(idCard == ''){
    		$("#idCard").next("span.msg").text('请填写身份证号!');
    		return;
    	}
    	$("#idCard").next("span.msg").text('');
    	var remark = $("#remark").val();
    	$.ajax({
    		url:'book_order',
    		type:'post',
    		dataType:'json',
    		data:{roomTypeId:'${roomType.id }',name:name,mobile:mobile,idCard:idCard,remark:remark,arriveDate:arriveDate,leaveDate:leaveDate,price:price,yajin:yajin},
    		success:function(data){
    			if(data.type == 'success'){
    				$(".malog").show();
    				setTimeout(function(){
    					window.location.href = 'index';
    				},1000)
    			}else{
    				alert(data.msg);
    			}
    		}
    	});
    })
  });
  </script>
</html>