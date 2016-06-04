/**
 * Created by m on 2016/5/30.
 */
angular
		.module('managementDanmuModule', [])
		.controller(
				'ManagementDanmuCtrl',
				function($scope, $state, $stateParams, $http, $filter) {
					console.log($stateParams.vid)

					$http
							.get(
									server + "demooListGet?vid="
											+ $stateParams.vid + "&time=0")
							.success(
									function(response) {
										console.log(response);
										var danmuList = [];
										for (var i = 0; i < response.demooList.length; i++) {
											var temp = {
												did : 0,
												username : "",
												time : "",
												message : ""
											};
											temp.did = response.demooList[i].did;
											temp.username = response.demooList[i].username;
											temp.time = response.demooList[i].time;

											var content = JSON
													.parse(response.demooList[i].message);
											temp.message = content.text;
											danmuList[i] = temp;

										}
										$scope.danmus = danmuList;
										console.log(danmuList);

									}).error(function(error) {

							})

					var deleteItem = function(did) {
						for (var i = 0; i < $scope.danmus.length; i++) {
							if ($scope.danmus[i].did == did) {
								for (var j = i; j < $scope.danmus.length - 1; j++) {
									$scope.danmus[j] = $scope.danmus[j + 1];
								}
								$scope.danmus[$scope.danmus.length - 1] = null;
								$scope.danmus.length--;
								break;
							}
						}
					}

					$scope.deleteDanmu = function(did) {
						console.log(did);
						$http.get(server + "demooDelete?did=" + did).success(
								function(response) {
									console.log(response);
									if (response.error_type == 0) {
										layer.msg("已删除！");
										deleteItem(did);

									}
								}).error(function(error) {

						})

					}

					$scope.batchDeleteDanmus = function() {
						console.log($scope.danmus);
						var selectedDanmus = $filter('filter')(
								$scope.danmus, true);
						var danmusToDelete = [];
						for (var i = 0; i < selectedDanmus.length; i++) {
							var d = selectedDanmus[i];
							danmusToDelete.push(parseInt(d.did));
							console.log(d.did);
						}

						var json = JSON.stringify(
								{"didList" : danmusToDelete}
					//		danmusToDelete
						);
//						json = "'" + json + "'";
						 //json = JSON.parse(json);
						console.log(json);
						$http
								.post(
										server + 'demooBulkDelete',
										json,
										{
											headers : {
												'Content-Type' : 'application/json; charset=UTF-8'
											},
											transformRequest : function(data) {
												return data;
											}
										}).success(function(response) {
									console.log(response);
									if(response.error_type == 0) {
										localDeleteDanmus(danmusToDelete);
										layer.msg("删除成功!");
									}
								}).error(function(error) {
									console.log(error);
									layer.msg("删除失败...");

								});
					};
					
					var localDeleteDanmus = function(danmus) {
						for (var i = 0; i < $scope.danmus.length; i++) {
							for (var j = 0; j < danmus.length; j++) {
								if (danmus[j] == $scope.danmus[i].did) {
									$scope.danmus.splice(i, 1);
								}
							}
						}
					};

				});