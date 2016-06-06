/**
 * Created by HF Q on 2016/4/24.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");
angular.module('videoGalleryModule', [])
    .controller('VideoGalleryCtrl', ['$scope','$http',"$location", function ($scope,$http,$location) {
        var limit = 4;
        $scope.curPage = 0;
        $scope.generateContent = generateContent;
        $scope.gotoPage = gotoPage;
        $scope.toVideoPage = function(vid){
            $location.path('/app/video/'+vid);
        }
        var start = $scope.curPage * limit;

        generateContent(start, limit);

        //helper function

        function gotoPage(page) {
            if (page < 0 || page >= $scope.totalPage || page === $scope.curPage) {
                return;
            }
            $scope.curPage = page;
            generateContent($scope.curPage * limit, limit);
        }

        function generateContent(start, limit) {
            var url = server + "videoListGet?start=" + start + "&limit=" + limit;
            if (!$scope.videos) {
                $scope.videos = [];
            }
            $http.get(url)
                .success(function (response) {
                    if (response.error_type == 0) {
                        console.log(response);
                        $scope.videos = response.videoList;

                        console.log($scope.videos);

                        var reg = undefined;

                        var scripts = $scope.videos;
                        var date = [], slides = [], slide;
                        var line = 0;
                        for (var i = 0; i < scripts.length;) {
                            slide = [];
                            var time = scripts[i].formatTime.substring(0, 7);
                            for (var c = 0; c < 2 && i < scripts.length;) {
                                if (reg === undefined || !scripts[i].formatTime.match(reg)) {
                                    if (slide.length != 0) {
                                        date.push({
                                            date: time,
                                            scripts: slides
                                        });
                                    }
                                    time = scripts[i].formatTime.substring(0, 7);
                                    reg = new RegExp("^" + time + ".*");
                                    continue;
                                }
                                if (i == scripts.length - 1) {//The last of video
                                    date.push({
                                        date: time,
                                        scripts: slides
                                    });
                                }
                                slide.push(scripts[i]);
                                c++;
                                i++;
                            }
                            slides.push({
                                line: line++,
                                slide: slide
                            });
                        }
                        $scope.videos = date;
                        //alert(date[0].scripts[0].length);
                        //$scope.totalPage = Math.ceil(JSON.parse(response.data).count/limit);
                        $scope.totalPage = Math.ceil(response.total/limit);
                        //alert($scope.totalPage);
                        if ($scope.totalPage == 0) {
                            $scope.totalPage = 1;
                        }
                        $scope.pageRange = [];
                        if ($scope.curPage - 5 < 0) {
                            for (var i = 0; i < $scope.curPage; i++) {
                                $scope.pageRange.push(i);
                            }
                        } else {
                            for (var i = $scope.curPage - 4; i < $scope.curPage; i++) {
                                $scope.pageRange.push(i);
                            }
                        }
                        //	            $scope.pageRange.push($scope.curPage);
                        if ($scope.curPage + 4 < $scope.totalPage) {
                            for (var i = $scope.curPage; i <= $scope.curPage + 4; i++) {
                                $scope.pageRange.push(i);
                            }
                        } else {
                            for (var i = $scope.curPage; i < $scope.totalPage; i++) {
                                $scope.pageRange.push(i);
                            }
                        }


                    }
                })
                .error(function(error){

                })

        }

    }])