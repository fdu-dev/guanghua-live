/**
 * Created by HF Q on 2016/3/23.
 */
var src = "https://www.apple.com/media/cn/watch/2015/a718f271_b19c_47d8_928d_d108fc5d702a/tv-spots/kiss/watch-kiss-cn-20151021_1280x720h.mp4";
var src2 = "level5.mp4"
var server = window.localStorage? localStorage.getItem("serverAddress"): Cookie.read("serverAddress");
angular.module('videoModule', ['ngRoute'])
    .run(function () {
    	
    })
    .controller('VideoCtrl', ['$rootScope','$scope','$http','$routeParams', function ($rootScope, $scope, $http,$routeParams) {
    	//window.location.reload();
        $http.get(server + "videoGet?vid="+$routeParams.vid)
            .success(function (response) {
                datas = response.video;
             //   console.log(response);
                console.log(datas);
                $scope.videoTitle = datas.title;
                $scope.videoDescription = datas.description;
                $scope.videoFrom = datas.department;


                if(window.player){
                	window.player.destroy();
                    delete(window.player);
                }
                $rootScope.player = $("#danmup").DanmuPlayer({

                    src: datas.url,       //视频源

                    width: 680,            //视频宽度

                    height: 460,            //视频高度

                    urlToGetDanmu: "",     //用来接收弹幕信息的url  (稍后介绍)

                    urlToPostDanmu: server+"demooSave?vid="+$routeParams.vid    //用来存储弹幕信息的url  (稍后介绍)                  

                });

//                window.playerInterval = window.setInterval(function(){console.log(window.player.current)}, 1000);
                
                $http.get(server+"demooListGet?vid="+$routeParams.vid+"&time=0").success(function(response){
                       console.log(response);
                    var danmuList=[];
                    for (var i=0;i<response.demooList.length;i++){
                        danmuList[i] =  JSON.parse(response.demooList[i].message);
                        danmuList[i].color = "#"+danmuList[i].color;
                    }
                    console.log(danmuList);
                    $("#danmup-danmu-div").danmu("addDanmu",danmuList)
                    //  $(".danmu-video").css({src:datas.url});
                }).error(function(error){

                })

            }).error(function (error) {
                console.log(error);
            });

        



    }]);

//DanmuPlayer (//github.com/chiruom/danmuplayer/) - Licensed under the MIT license
