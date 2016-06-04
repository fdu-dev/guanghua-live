/**
 * Created by HF Q on 2016/5/20.
 */
var server = window.localStorage ? localStorage.getItem("serverAddress")
		: Cookie.read("serverAddress");

var clipboardData = window.clipboardData;
angular
		.module('liveAdminModule', [])
		.controller(
				'LiveAdminCtrl',
				[
						'$cookies',
						'$rootScope',
						'$state',
						'$scope',
						'$http',
						'$interval',
						function($cookies, $rootScope, $state, $scope, $http,
								$interval) {
							var isLogin = window.localStorage ? localStorage
									.getItem("isLogin") : Cookie
									.read("isLogin");
							if (!(isLogin == "login")) {
								layer.msg("请先登录！");
								$state.go('login');
							}

							var file;

							$scope.fileSelected = function() {
								console.log("dd");
								file = document.getElementById('file').files[0];
								if (file) {
									console.log("ee");
									if (file.type.substring(0, 5) == "image") {
										image_file = file;
										var reader = new FileReader();
										reader.readAsDataURL(file);
										reader.onload = function(e) {
											var urlData = this.result;
											document
													.getElementById("converImg")
													.setAttribute("src",
															urlData);
										}
									} else {
										layer.msg("只能上传图片...年轻人不要调皮！");
									}
								}
							}

							$scope.confirm = function() {
								// $('#live_form').submit();
								var coverImg = file;
								var title = $scope.title;
								var description = $scope.description;

								if (coverImg == undefined) {
									layer.msg("请上传封面图片！");
									return;
								}
								if (!(coverImg.type.substring(0, 5) == "image")) {
									layer.msg("只能选择图片作为封面图！");
									return;
								}
								if (title == "" || description == ""
										|| title == undefined
										|| description == undefined) {
									layer.msg("请填写直播间标题和简介！");
									return;
								}

								var formData = new FormData();
								formData.append("coverImg", coverImg);
								formData.append("title", title);
								formData.append("description", description);

								console.log(formData);

								$scope.key = "请稍等，key获取中...";

								var xmlhttp = null;
								if (window.XMLHttpRequest) {// code for all new browsers
									xmlhttp = new XMLHttpRequest();
								} else if (window.ActiveXObject) {// code for IE5 and IE6
									xmlhttp = new ActiveXObject(
											"Microsoft.XMLHTTP");
								}
								if (xmlhttp != null) {
									xmlhttp.open("POST", server + "liveStart",
											true);
									xmlhttp.onreadystatechange = state_Change;
									xmlhttp.send(formData);
								} else {
									alert("Your browser does not support XMLHTTP.");
								}

								var setKey = function(key) {
									console.log("ddd");
									$scope.key = key;
									$scope.$apply();
								}

								function state_Change() {
									if (xmlhttp.readyState == 4) {// 4 = "loaded"
										if (xmlhttp.status == 200) {// 200 = OK
											// ...our code here...
											var response = xmlhttp.response;
											response = JSON.parse(response);
											//  console.log(response.error_type);
											if (response.error_type == 0) {
												//	 console.log(response.key);
												setKey(response.key);

											}
										} else {
											alert("Problem retrieving XML data");
										}
									}
								}
							}

							//$scope.confirm = function () {
							//    var coverImg = file;
							//    var title = $scope.title;
							//    var description = $scope.description;
							//    console.log(title + " " + description);
							//    if (coverImg == undefined) {
							//        layer.msg("请上传封面图片！");
							//        return;
							//    }
							//    if (!(coverImg.type.substring(0, 5) == "image")) {
							//        layer.msg("只能选择图片作为封面图！");
							//        return;
							//    }
							//    if (title == "" || description == "" || title == undefined || description == undefined) {
							//        layer.msg("请填写直播间标题和简介！");
							//        return;
							//    }
							//    $scope.key = "key获取中...";
							//
							//            var formData = new FormData();
							//            formData.append("coverImg",coverImg);
							//            formData.append("title", title);
							//            formData.append("description", description);
							//        
							//            console.log(formData);
							//        
							//            var xmlhttp = null;
							//            if (window.XMLHttpRequest) {// code for all new browsers
							//                xmlhttp = new XMLHttpRequest();
							//            }
							//            else if (window.ActiveXObject) {// code for IE5 and IE6
							//                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
							//            }
							//            if (xmlhttp != null) {
							//                xmlhttp.open("POST", server+"liveStart", true);
							//                xmlhttp.setRequestHeader("cookie","23333");
							//                xmlhttp.setRequestHeader("cookie","23333");
							//                xmlhttp.onreadystatechange = state_Change;
							//                xmlhttp.send(formData);
							//            }
							//            else {
							//                alert("Your browser does not support XMLHTTP.");
							//            }
							//            
							//            function state_Change() {
							//                if (xmlhttp.readyState == 4) {// 4 = "loaded"
							//                    if (xmlhttp.status == 200) {// 200 = OK
							//                        // ...our code here...
							//                        console.log(xmlhttp);
							//                    }
							//                    else {
							//                        alert("Problem retrieving XML data");
							//                    }
							//                }
							//            }

							//
							//    $.ajax({
							//        url : server+"liveStart",
							//        type : 'POST',
							//        data : formData,
							//
							//        /**
							//         * 必须false才会避开jQuery对 formdata 的默认处理
							//         * XMLHttpRequest会对 formdata 进行正确的处理
							//         */
							//        processData : false,
							//        /**
							//         *必须false才会自动加上正确的Content-Type
							//         */
							//        contentType : false,
							//        success : function(responseStr) {
							//            alert("成功：" + JSON.stringify(responseStr));
							//            //                  var jsonObj = $.parseJSON(responseStr);//eval("("+responseStr+")");
							//        },
							//        error : function(responseStr) {
							//            alert("失败:" + JSON.stringify(responseStr));//将    json对象    转成    json字符串。
							//        }
							//    });
							//
							//
							//    //$http.post(server + "liveStart",
							//    //    formData,
							//    //    {
							//    //        headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
							//    //        transformRequest: function (data) {
							//    //            return data;
							//    //        }
							//    //    })
							//    //    .success(function (response) {
							//    //        console.log(response);
							//    //        if (response.error_type == 0) {
							//    //
							//    //
							//    //        }
							//    //    })
							//    //    .error(function (error) {
							//    //        console.log(error)
							//    //    })
							//
							//
							//    function getCookie(name)
							//    {
							//        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
							//
							//        if(arr=document.cookie.match(reg))
							//
							//            return unescape(arr[2]);
							//        else
							//            return null;
							//    }
							//
							//}

						} ])