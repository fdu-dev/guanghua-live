
var module = document.getElementById("module");
var lastTime = document.getElementById("time");

document.getElementById("submit").addEventListener("click", function() {
	var uploadHTTP;
	if(window.ActiveXObject) {
		uploadHTTP=new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	else if(window.XMLHttpRequest) {
		uploadHTTP=new XMLHttpRequest;
	}
	var url="http://localhost:8080/GuangHuaLive/register";
	//var url = "http://msg.umeng.com/api/send?sign=";
	uploadHTTP.open("POST",url,false);
	var str2='username=ccg&password=123456789'
	
	uploadHTTP.setRequestHeader("Content-type","application/json");
	uploadHTTP.send(str2);
	uploadHTTP.onreadystatechange=function(){
		if(uploadHTTP.readyState==4 && uploadHTTP.status==200){
			console.log(uploadHTTP.responseText);
			alert("!23");
		}
	}


})