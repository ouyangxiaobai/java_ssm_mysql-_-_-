(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

//js  webcontext
function getContextPath(){
	var  webroot=document.location.href;
    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
 	webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
    webroot=webroot.substring(0,webroot.indexOf('/'));
    var contextPath="/"+webroot;
    return contextPath;
    //alert(contextPath);
}

//CheckBox
function selectAll(formName, cbName) {
	var o = document.forms(formName).item(cbName);
	if (o.length) {//
		for (i=0; i<o.length; i++) {
			document.forms(formName).item(cbName)[i].checked = true;
		}
	} else {
		o.checked = true;
	}
}
//CheckBox
function unSelectAll(formName, cbName) {
	var o = document.forms(formName).item(cbName);
	if (o.length) {//
		for (i=0; i<o.length; i++) {
			document.forms(formName).item(cbName)[i].checked = false;
		}
	} else {
		o.checked = false;
	}
}


function validateCheckbox(cbName,alt) {
	var val=$("input:checkbox[name='"+cbName+"']:checked").size();
	if(val==0){
    	showMsgModal("提示",alt);
    	return false;
    }else{
        return true;
    }
}
function validateRadio(raName,alt) {
	var val=$("input:radio[name='"+raName+"']").is(":checked");
	if(val==false){
    	showMsgModal("提示",alt);
    	return false;
    }else{
        return true;
    }	
}

function showMsgModal(title,body){
	$("#msgTitle").html(title);
	$("#msgBody").html(body);
	$("#msgModal").modal();
}