/**
 * Created by HF Q on 2016/4/25.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress") : Cookie.read("serverAddress");
angular.module('registerModule', [])
    .controller('RegisterCtrl', ['$scope', '$http', '$location', function ($scope, $http, $location) {

        $scope.register_uploadImg = function () {
            $('#register-upload-file-hidden').trigger('click');
        }

        var head_changed_flag = false;
        $scope.register_preview_head = function () {
            var file = document.getElementById("register-upload-file-hidden").files[0];
            if (file) {
                if (file.type.substring(0, 5) == "image") {
                    head_changed_flag = true;
                    image_file = file;
                    var reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onload = function (e) {
                        var urlData = this.result;
                        document.getElementById("register-avatar-img").setAttribute("src", urlData);
                    }
                }
                else {
                    alert("只能上传图片...年轻人不要调皮！");
                }
            }
        }


        $scope.signUp = function () {
            var username = $scope.registerProfileUsername;
            var password = $scope.registerProfilePassword;
            var email = $scope.registerProfileEmail;
            var department = $scope.registerProfileDepartment;
            var phone = $scope.registerProfilePhone;
            $http.post(server + "userRegister",
                {
                    username: username,
                    password: password,
                    email: email,
                    department: department,
                    phone: phone
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
                        alert("注册成功!");
                        $location.path('/app/login');
                    }
                })
                .error(function (error) {
                    console.log(error);
                })
        }
    }])