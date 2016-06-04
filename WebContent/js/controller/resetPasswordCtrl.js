/**
 * Created by HF Q on 2016/5/9.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");

angular.module('resetPasswordModule',[])
.controller('ResetPasswordCtrl',['$scope','$http','$routeParams','$location', function($scope,$http,$routeParams,$location){
    var uid = $routeParams.uid;

    $scope.submitChange = function(){
        if ($scope.newPassword != $scope.confirmPassword) {
            layer.msg("两次输入密码不一致!");
            return;
        }
        console.log('dd');
        $http.post(server + "confirmRetrivePassword",
            {
                uid: uid,
                password: $scope.newPassword
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
                    alert("修改成功，请登录!");
                    $location.path('/app/main');
                }
            })
            .error(function (error) {
                console.log(error);
            })
    }
}])