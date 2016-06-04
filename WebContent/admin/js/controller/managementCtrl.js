/**
 * Created by m on 2016/5/25.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");


angular.module('managementModule',[])
    .controller('ManagementCtrl', function ($scope, $state,$rootScope,$http) {
        var isLogin = window.localStorage ? localStorage.getItem("isLogin") : Cookie.read("isLogin");
        $scope.userType = window.localStorage ? localStorage.getItem("userType") : Cookie.read("userType");
        if (!(isLogin == "login")) {
            layer.msg("请先登录！");
            $state.go('login');
        }

        $scope.edit = function () {
            // Here we show off go's ability to navigate to a relative state. Using '^' to go upwards
            // and '.' to go down, you can navigate to any relative state (ancestor or descendant).
            // Here we are going down to the child state 'edit' (full name of 'contacts.detail.item.edit')
            $state.go('.edit');
        };

        var deleteItem =  function(vid){
            for (var i=0;i<$scope.videos.length;i++){
                if ($scope.videos[i].vid == vid) {
                    for (var j=i;j<$scope.videos.length-1;j++){
                        $scope.videos[j] = $scope.videos[j+1];
                    }
                    $scope.videos[$scope.videos.length-1] = null;
                    $scope.videos.length--;
                    break;
                }
            }
        }

        $scope.deleteVideo = function (vid) {
        	console.log(vid);
            $http.get(server+'videoDelete?vid='+vid)
                .success(function(response){
                    console.log(response);
                    if (response.error_type == 0) {
                    	 deleteItem(vid);
                         layer.msg("已删除！");
                    }             
                });
        };
        var username = window.localStorage ? localStorage.getItem("username") : Cookie.read("username");
        $http.get(server+'videoListGetByUsername?username='+username)
            .success(function(response){
                console.log(response);
                $scope.videos = response.videoList;
            });


    });