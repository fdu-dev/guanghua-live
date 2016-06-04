/**
 * Created by HF Q on 2016/5/9.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");

angular.module('forgetPasswordModule',[])
.controller('ForgetPasswordCtrl',['$scope','$http','$location', function($scope,$http,$location){
    //jquery 正则验证
	$scope.myemail = "";
    $scope.getBackPwd = function(){
     	if(!$scope.myemail){
    		layer.msg("请输入邮箱！");
    		return;
    	}
    	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@fudan\.edu\.cn$/;
        if (!myreg.test($scope.myemail)) {
            layer.msg("邮箱不合法！");
            return;
        }
    	$http.post(server + "retrivePassword",
                {
                    email: $scope.myemail
                },
                {
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    transformRequest: function (data) {
                        return $.param(data);
                    }
                })
                .success(function (response) {
                    console.log(response);
                    if (response.error_type == 0) {
                        console.log("register successfully");
                        alert("请到邮箱中查收重置密码的链接!");
                        $location.path('/app/main');
                    }
                })
                .error(function (error) {
                    console.log(error);
                })
    }
}])