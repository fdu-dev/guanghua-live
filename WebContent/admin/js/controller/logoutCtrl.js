/**
 * Created by HF Q on 2016/5/31.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");
angular.module('logoutModule',[])
.controller('LogoutCtrl',['$scope','$state','$http','$rootScope',function($scope,$state,$http,$rootScope){

    $scope.logout = function(){

        var isLogin = window.localStorage ? localStorage.getItem("isLogin") : Cookie.read("isLogin");
        if (!(isLogin == "login")) {
            layer.msg("请先登录！");
            $state.go('login');
        }

        $http.get(server+"/userLogout")
            .success(function(response){
                if (response.error_type == 0) {
                    $rootScope.isLogin = false;
                    if (window.localStorage) {
                        console.log("localStorage ", "login");
                        localStorage.setItem("isLogin", "offline");
                        localStorage.setItem("username", "");
                    } else {
                        console.log("cookie");
                        Cookie.write("isLogin", "offline");
                        Cookie.write("username", "");
                    }
                    document.getElementById('upName').innerHTML = "未登录";
                    $state.go('login');
                }
            })
            .error(function(error){

            })
    }
}])