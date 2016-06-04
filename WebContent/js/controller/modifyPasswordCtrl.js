/**
 * Created by HF Q on 2016/4/26.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");
angular.module('modifyPasswordModule',[])
.controller('ModifyPasswordCtrl',['$scope','$http','$location', function($scope,$http,$location){
    var oldPwd = $scope.oldPassword;
    var newPwd = $scope.newPassword;

    $scope.submitChange = function(){
        if ($scope.newPassword != $scope.confirmPassword) {
            layer.msg("两次输入密码不一致!");
            return;
        }
        $http.post(server+"userUpdate",
            {
	        	oldPassword:$scope.oldPassword,
	        	newPassword:$scope.newPassword,
            },
            {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                transformRequest:function(data){
                    return $.param(data);
                }
            })
            .success(function(response){
                if (response.error_type == 0){
                    layer.msg("修改成功！");
                    $location.path('/app/profile');
                }
            })
            .error(function(error){

            })
    }
}])