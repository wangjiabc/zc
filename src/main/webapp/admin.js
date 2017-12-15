function getCookie(name){ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
  }


if(getCookie("type")==0)
 $('#brand').text("用户 : "+getCookie("campusAdmin")+" (管理员)");
else
  $('#brand').text("用户 : "+getCookie("campusAdmin")+" (普通商户)");

//定义全局变量
var adminType = getCookie("type");		//当前登录账号类型（总管理员、校区管理员）
console.log('admintype='+adminType);
var campusId = getCookie("campusId");	
var status = getCookie("status");		//校区状态
console.log(document.cookie);

//定义通用方法

function setSuccess(message) {
	if (!message) {
		$("#operate-success").text("Well, it works!");
	} else {
		$("#operate-success").text(message);
	}
	//$("#operate-success").show();
	$("#operate-success").css("visibility", "visible");
	window.setTimeout(function() {
		//$("#operate-success").hide();
		$("#operate-success").css("visibility", "hidden");
	}, 2000);
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }
