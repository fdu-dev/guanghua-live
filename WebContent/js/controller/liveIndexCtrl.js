/**
 * Created by HF Q on 2016/4/24.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");

angular.module('liveIndexModule',[])
.controller('LiveIndexCtrl',['$scope','$location','$http',function($scope,$location,$http){
    $scope.goGallery = function() {
        $location.path('/app/gallery');
    }
    $scope.goLive = function(vid){
        console.log("click");
        $location.path('/app/live/'+vid);
    }
    $scope.toVideoPage = function(vid){
        $location.path('/app/video/'+vid);
    }

    var start = 0;
    var limit = 8;
    var url = server + "videoListGet?start=" + start + "&limit=" + limit;
    if (!$scope.videos) {
        $scope.videos = [];
    }

    //直播的数据获取
    var liveUrl = server + "liveListGet?start=0&limit=-1";

    $http.get(liveUrl)
        .success(function(response){
            //alert(JSON.stringify(response));
                $scope.liveList = response.videoList;
            //alert($scope.liveList);
            //alert($scope.liveList.length);
        })
        .error(function(response){
            console.log("Something error when getting liveVideoList.");
            console.log(response);
        });

    $http.get(url)
        .success(function (response) {
            if (response.error_type == 0) {
                console.log(response);
                var scripts = response.videoList;

                //console.log($scope.videos);

                var slide = [];
                $scope.videos = [];
                var line = 0;
                for(var i=0; i < scripts.length;){
                    slide = [];
                    for(var countOfLine=0; countOfLine<2&&i < scripts.length;){
                        slide[countOfLine] = scripts[i];
                        countOfLine++;
                        i++;
                    }
                    $scope.videos[$scope.videos.length]={
                        slide:slide,
                        line:line
                    };
                    line++;
                }
                //alert(JSON.stringify($scope.videos));
            }
        })
        .error(function(error){

        })

}])