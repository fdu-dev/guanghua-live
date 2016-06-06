/**
 * Created by HF Q on 2016/4/22.
 */

var server = window.localStorage? localStorage.getItem("serverAddress"): Cookie.read("serverAddress");
angular.module('headerModule',['registerModule','loginModule','pageSwitchModule'])
.directive('appHeader',function(){
    return{
        restrict:'EA',
        templateUrl:"template/directiveTpl/header.tpl.html"
    };
})
.controller('HeaderCtrl',['$scope','$location','$rootScope', '$http', function($scope,$location,$rootScope,$http){
	$scope.toMain = function(){
		$location.path('#/app/main');
	}
    $http.get(server+"userStatusGet")
        .success(function(response){
            if (response.error_type == 0) {
            	$('#centerBtn').show();
                $rootScope.isLogin = true;
            } else {
            	$('#centerBtn').hide();
                $('#topBtn1').html("注册");
                $('#topBtn2').html("登录");
                $rootScope.isLogin = false;
            }
        })
        .error(function(error){

        })


    $scope.signUp = function () { 
    	$location.path('/app/register');
    };
    $scope.signIn = function () {
    	sessionStorage.removeItem('user');
    	$location.path('/app/login');
    }
    
    $scope.signOut = function(){
    	$http.get(server+"/userLogout")
        .success(function(response){
            if (response.error_type == 0) {
            	$('#centerBtn').hide();
                $rootScope.isLogin = false;
                if (window.localStorage) {
                    console.log("localStorage ", "login");
                    localStorage.setItem("isLogin", "offline");
                } else {
                    console.log("cookie");
                    Cookie.write("isLogin", "offline");
                }
                var a = window.location.href.toString();
                a =a.split('/');
                if (a[a.length-1] == "profile" || a[a.length-1] == "modifyPassword") {
                	$location.path('/app/login');
                }
              
            }
        })
        .error(function(error){

        })
    }

    $scope.goProfile = function(){
        $location.path('/app/profile');
    }
    $scope.goChangePwd = function(){
        $location.path('/app/modifyPassword');
    }
}])