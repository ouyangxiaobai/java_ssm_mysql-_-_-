utils = {
    setParam : function (name,value){
        localStorage.setItem(name,value)
    },
    getParam : function(name){
        return localStorage.getItem(name)
    },
    clearParam : function(){
    	localStorage.clear();
    }
}

goods={
    id:0,
    cateId:0,
    name:"",
    price:0.0,
    discount:0.0,
    postalfee:0.0,
    size:"",
    color:"",
    num:0,
    pic:""
};
orderdetail={
    username:"",
    phone:"",
    address:"",
    zipcode:"",
    totalNumber:0,
    totalAmount:0.00    
}
cart = {
    //向购物车中添加商品
    addGoods:function(goods){
        var ShoppingCart = utils.getParam("ShoppingCart");
        if(ShoppingCart==null||ShoppingCart==""){
			//第一次加入商品
            var jsonstr = {"goodsList":[{"id":goods.id,"cateId":goods.cateId,"name":goods.name,"price":goods.price,
            							"discount":goods.discount,"postalfee":goods.postalfee,
            							"size":goods.size,"color":goods.color,"num":goods.num,"pic":goods.pic}],
            				"totalNumber":goods.num,"totalAmount":(goods.discount*goods.num)};
            utils.setParam("ShoppingCart","'"+JSON.stringify(jsonstr));
        }else{
            var jsonstr = JSON.parse(ShoppingCart.substr(1,ShoppingCart.length));
            var goodsList = jsonstr.goodsList;
            var result=false;
			//查找购物车中是否有该商品
            for(var i in goodsList){
                if(goodsList[i].id==goods.id && goodsList[i].size==goods.size && goodsList[i].color==goods.color){
                	goodsList[i].num=parseInt(goodsList[i].num)+parseInt(goods.num);
                    result = true;
                }
            }
            
            if(!result){
				//没有该商品就直接加进去
            	goodsList.push({"id":goods.id,"cateId":goods.cateId,"name":goods.name,"price":goods.price,"discount":goods.discount,
            					"postalfee":goods.postalfee,"size":goods.size,"color":goods.color,"num":goods.num,"pic":goods.pic});
            }
			//重新计算总价
            jsonstr.totalNumber=parseInt(jsonstr.totalNumber)+parseInt(goods.num);
            jsonstr.totalAmount=parseFloat(jsonstr.totalAmount)+(parseInt(goods.num)*parseFloat(goods.discount));
            orderdetail.totalNumber = jsonstr.totalNumber;
            orderdetail.totalAmount = jsonstr.totalAmount;
            //保存购物车
            utils.setParam("ShoppingCart","'"+JSON.stringify(jsonstr));
        }
    },
    //修改购买商品数量
    updateGoodsNum:function(index,num){
        var ShoppingCart = utils.getParam("ShoppingCart");
        var jsonstr = JSON.parse(ShoppingCart.substr(1,ShoppingCart.length));
        var goodsList = jsonstr.goodsList;
        
        for(var i in goodsList){
            if(i==index){
                jsonstr.totalNumber=parseInt(jsonstr.totalNumber)+(parseInt(num)-parseInt(goodsList[i].num));
                jsonstr.totalAmount=parseFloat(jsonstr.totalAmount)+((parseInt(num)*parseFloat(goodsList[i].discount))-parseInt(goodsList[i].num)*parseFloat(goodsList[i].discount));
                goodsList[i].num=parseInt(num);
                
                orderdetail.totalNumber = jsonstr.totalNumber;
                orderdetail.totalAmount = jsonstr.totalAmount;
                utils.setParam("ShoppingCart","'"+JSON.stringify(jsonstr));
                return;
            }
        }
    },
    //获取购物车中的所有商品
    getGoodsList:function(){
        var ShoppingCart = utils.getParam("ShoppingCart");
        if(ShoppingCart==null||ShoppingCart==""){
        	return false;
        }
        var jsonstr = JSON.parse(ShoppingCart.substr(1,ShoppingCart.length));
        var goodsList = jsonstr.goodsList;
        orderdetail.totalNumber = jsonstr.totalNumber;
        orderdetail.totalAmount = jsonstr.totalAmount;
        return goodsList;
        
    },
    //判断购物车中是否存在商品
    existGoods:function(id,size,color){
        var ShoppingCart = utils.getParam("ShoppingCart");
        var jsonstr = JSON.parse(ShoppingCart.substr(1,ShoppingCart.length));
        var goodsList = jsonstr.goodsList;
        var result=false;
        for(var i in goodsList){
            if(goodsList[i].id==id && goodsList[i].size==size && goodsList[i].color==color){
                result = true;
            }
        }
        return result;
    },
    //删除购物车中商品
    deleteGoods:function(index){
        var ShoppingCart = utils.getParam("ShoppingCart");
        var jsonstr = JSON.parse(ShoppingCart.substr(1,ShoppingCart.length));
        var goodsList = jsonstr.goodsList;
        var list=[];
        for(var i in goodsList){
            if(i==index){
                jsonstr.totalNumber=parseInt(jsonstr.totalNumber)-parseInt(goodsList[i].num);
                jsonstr.totalAmount=parseFloat(jsonstr.totalAmount)-parseInt(goodsList[i].num)*parseFloat(goodsList[i].discount);
            }else{
                list.push(goodsList[i]);
            }
        }
        jsonstr.goodsList = list;
        orderdetail.totalNumber = jsonstr.totalNumber;
        orderdetail.totalAmount = jsonstr.totalAmount;
        utils.setParam("ShoppingCart","'"+JSON.stringify(jsonstr));
    },
    //清空购物车中商品
    clearGoods:function(){
        utils.clearParam();
        orderdetail.totalNumber=0;
        orderdetail.totalAmount=0;
    }
};



//加入到购物车
function addCart(num,size,color){
	var goodsId = $("#goodsId").html();
	var cateId = $("#cateId").html();
	var goodsName = $("#goodsName").html(); 
	var goodsPrice = $("#goodsPrice").html();
	var goodsDiscount = $("#goodsDiscount").html();
	var goodsPostalfee = $("#goodsPostalfee").html();
	var pic = $("#pic").html();
	//alert(goodsId+"  "+goodsName+"  "+goodsPrice+"  "+goodsDiscount+"  "+goodsPostalfee+"  "+pic);

	var goods = { 
	    'id': goodsId,     
	    'cateId':cateId,
	    'name': goodsName, 
	    'price':goodsPrice, 
	    "discount":goodsDiscount,
	    "postalfee":goodsPostalfee,
	    "size":size,
	    "color":color,
	    "num":num,
	    "pic":pic 	    
	}; 

	//商品加入到购物车 
	cart.addGoods(goods); 
	$("#msgTitle").html("添加成功");
	$("#msgBody").html("已将商品加入到购物车");
	$("#msgModal").modal(); 

	
/*	if(db){
		//执行sql脚本
		db.transaction(function(trans) {
			
			var newGoods=trans.executeSql("select * from cart where goodsName=? and size=? and color=?",[goodsName,size,color], 
			function (ts, data) {
				if(data){
					if(data.rows.length==0){
						trans.executeSql("insert into cart values(?,?,?,?,?,?,?,?,?)",[goodsId,goodsName,goodsPrice,goodsDiscount,goodsPostalfee,size,color,num,pic], 
							function (ts, data) {
								$("#msgTitle").html("添加成功");
								$("#msgBody").html("已将商品加入到购物车");
								$("#msgModal").modal();
							}, 
							function (ts, message) {
								$("#msgTitle").html("添加失败");
								$("#msgBody").html("添加到购物车失败");
								$("#msgModal").modal();
							});
					}
					else{
						trans.executeSql("update cart set num=num+? where goodsname=? and size=? and color=?",[num,goodsName,size,color], 
							function (ts, data) {
								$("#msgTitle").html("添加成功");
								$("#msgBody").html("已将商品加入到购物车");
								$("#msgModal").modal();
							}, 
							function (ts, message) {
								$("#msgTitle").html("添加失败");
								$("#msgBody").html("添加到购物车失败");
								$("#msgModal").modal();
							});
					}
				}
			}, 
			function (ts, message) {
				$("#msgTitle").html("添加失败");
				$("#msgBody").html("添加到购物车失败");
				$("#msgModal").modal();
				return false;
			});
		});
	}*/
}


