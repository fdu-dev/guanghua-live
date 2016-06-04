/**
 * Created by HF Q on 2016/4/26.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");
angular.module('profileModule',['lr.upload'])
.controller('ProfileCtrl',['$scope','$http','$location',function($scope,$http, $location){
    var user = sessionStorage.getItem('user');
    user = JSON.parse(user);
    if(user==undefined){
        $location.path('app/login')
    }
    $scope.userUploadHeadImgAction = server+"userUploadHeadImg";
    $scope.userProfileHeadImg = "img/head.png";
//    alert(server+"userGet");
    $http.get(server+"userGet")
        .success(function(response){
//        	alert(JSON.stringify(response));
            console.log(response);
            if (response.error_type == 0) {
                $scope.userProfileUsername = response.user.username;
                $scope.userProfileEmail = response.user.email;
                if (response.user.headImg&&response.user.headImg!=null&&response.user.headImg!="") {
                    $scope.userProfileHeadImg = response.user.headImg;
                }
                $scope.userProfileDepartment = response.user.department;
                $scope.userProfilePhone = response.user.phone;
//                alert(JSON.stringify(response.user));
                sessionStorage.setItem('user', JSON.stringify(response.user));
            }
        })
        .error(function(error){
    
        })
    $scope.userProfileUsername = user.username;
    $scope.userProfileEmail = user.email;
    if (user.headImg&&user.headImg!=null&&user.headImg!="") {
        var img = server+"/"+user.headImg;
        //alert('\\');
        img = img.replace("\\","/");
//        alert(img);
        $scope.userProfileHeadImg = img;
        //$scope.userProfileHeadImg.replaceAll("\\\\","/");
        //alert($scope.userProfileHeadImg);
    }
    $scope.userProfileDepartment = user.department;
    $scope.userProfilePhone = user.phone;

    function user_uploadImg(){
        $('#user-upload-file-hidden').trigger('click');
    }

    $scope.modifyUser = function(isValid){
        if(isValid){
            //alert(isValid);
            $http.post(server+'userModify',
                {
                    department:$scope.userProfileDepartment,
                    email:$scope.userProfileEmail,
                    phone:$scope.userProfilePhone
                },
                {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    transformRequest:function(data){
                        return $.param(data);
                    }
                })
            .success(function(response){
//                alert(JSON.stringify(response));
                sessionStorage.setItem('user', JSON.stringify(response.user));
                $location.path('/app/main');
            });
        }
    }

    $scope.chooseImg = function(){
        $('#user-upload-file-hidden').trigger('click');
    }

    var head_changed_flag=false;
    $scope.user_preview_head = function (){
        //alert('sdfsdfdfs');
        var file=document.getElementById("user-upload-file-hidden").files[0];
        if(file){
            if(file.type.substring(0,5)=="image"){
                head_changed_flag=true;
                image_file=file;
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function (e) {
                    var urlData = this.result;
                    document.getElementById("user-avatar-img").setAttribute("src",urlData);
                }
            }
            else{
                alert("只能上传图片...年轻人不要调皮！");
            }
        }
    }

    $scope.submit = function(){
        //alert($scope.userUploadHeadImgAction);
        $('#userAvatarForm').attr('action', $scope.userUploadHeadImgAction);
        $('#userAvatarForm').submit();
        //alert('uuuuuuuuuuuuuuuuuu');
    }

    $scope.onFileUploadSuccess = function(response){
//    	alert(JSON.stringify(response));
    	response = response.data;
    	if(response.error_type == 0){
    		var user = sessionStorage.getItem('user');
        	user = JSON.parse(user);
        	user.headImg = response.url;
        	sessionStorage.setItem('user', JSON.stringify(user));
        	$scope.userProfileHeadImg = response.url;
    	}else{
    		alert('Something wrong!');
    	}
    }
    
}])