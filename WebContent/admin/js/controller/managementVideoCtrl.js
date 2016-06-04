/**
 * Created by m on 2016/5/30.
 */
angular.module('managementVideoModule', ['lr.upload'])
    .controller('ManagementVideoCtrl', function ($scope, $state, $stateParams,$http) {
    	
    	var showMsg = function(msg){
    		layer.msg(msg);
    	}

        console.log("vid is "+$stateParams.vid);
        $http.get(server + "videoGet?vid="+$stateParams.vid)
            .success(function (response) {
                $scope.video = response.video;
                console.log($scope.video.coverImg);
                $scope.video.coverImg = "http://localhost:8080/GuangHuaLive/"+$scope.video.coverImg;
               
                console.log($scope.video.coverImg);
                $scope.imgUploadData = {
                    vid:$scope.video.vid
                };
            }).error(function (error) {
            console.log(error);
        });

        

        $scope.saveChange = function(){
        	var title = $('#video-title-input').val();
        	var description = $('#video-description-textarea').val();
        	
        	
        
        	
            $http.get(server + "videoUpdate?vid="+$stateParams.vid+"&title="+title+"&description="+description)
                .success(function (response) {
                	console.log(response);
                	if (response.error_type == 0) {
                		 $scope.video = response.video;
                		 showMsg("保存成功！");
                	}
                   

                }).error(function (error) {
                	console.log(error);
                });
        }


        $scope.onFileUploadSuccess = function(response){
    	//alert(JSON.stringify(response));
        	
            response = response.data;
            if(response.error_type == 0){
            	showMsg("上传成功！");
            	response.video.coverImg = server+response.video.coverImg; 
                $scope.video = response.video;
            }else{
                alert('Something wrong!');
            }
        }

    });

function uploadCover() {
    $('#video-cover-file').trigger('click');
}

var head_changed_flag = false;
previewVideoCover = function () {
    var file = document.getElementById("video-cover-file").files[0];
    if (file) {
        if (file.type.substring(0, 5) == "image") {
            head_changed_flag = true;
            image_file = file;
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                var urlData = this.result;
                document.getElementById("video-cover-img").setAttribute("src", urlData);
            }
        }
        else {
            alert("不要调皮!封面只能是图片！");
        }
    }
};