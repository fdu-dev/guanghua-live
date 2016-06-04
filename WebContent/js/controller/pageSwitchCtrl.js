/**
 * Created by HF Q on 2016/4/22.
 */
angular.module('pageSwitchModule',[])
.controller('PageSwitchCtrl',['$rootScope','$scope','$location',function($rootScope, $scope,$location){
    $scope.pages = [{"name": "直播", "index": "-0"}, {"name": "视频库"}];
    $scope.switch = function(item){
        console.log(item);
//        delele window.player;
//        clearInterval(window.playerInterval);
        if (item.name == '直播'){
            $location.path('/app/main');
        }
        if (item.name == '视频库') {
            $location.path('/app/gallery');
        }
    }
}]);