'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('admin-app', ['ngRoute', 'ngCookies', 'liveAdminModule', 'loginModule', 'logoutModule', 'managementModule', 'managementVideoModule', 'managementCommentModule', 'managementDanmuModule', 'ui.router', 'datatables']);
app.config(function($stateProvider,$urlRouterProvider) {
    $stateProvider
        .state('liveAdmin',{
            url: '/liveAdmin',
            views: {
                'liveAdmin': {
                    templateUrl: 'template/liveAdmin.tpl.html',
                    controller: 'LiveAdminCtrl'
                }
            }
        })
        .state('login',{
            url: '/login',
            views: {
                'login': {
                    templateUrl: 'template/login.tpl.html',
                    controller: 'LoginCtrl'
                }
            }
        })
        .state('management',{
            url:'/management',
            views:{
                'management': {
                    templateUrl: 'template/management.tpl.html',
                    controller: 'ManagementCtrl'
                }
            }
        })
        .state('management.video', {
            url: '/video?vid',
            views:{
                'management': {
                    templateUrl: 'template/management-video.tpl.html',
                    controller: 'ManagementVideoCtrl'
                }

            }
        })
        .state('management.video.comment', {
            url: "/comment",
            views:{
                'commentManagement':{
                    templateUrl: 'template/management-video-comment.tpl.html',
                    controller: 'ManagementCommentCtrl'
                }
            }
        })
        .state('management.video.danmu', {
            url: "/danmu",
            views:{
                'danmuManagement': {
                    templateUrl: 'template/management-video-danmu.tpl.html',
                    controller: 'ManagementDanmuCtrl'
                }
            }
        });
    $urlRouterProvider.otherwise('login');
});


app.run(['$rootScope', '$http', '$cookies', function ($rootScope, $http, $cookies) {
    console.log("reload");
    $rootScope.isLogin = false;
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
//    var server = "http://192.168.1.122:8080/GuangHuaLive/";
    //var server = "http://139.129.10.20:8080/GuangHuaLive/";
    var server = "http://localhost:8080/GuangHuaLive/";
    if (window.localStorage) {
        console.log("localStorage ", server);
        localStorage.setItem("serverAddress", server);
    } else {
        console.log("cookie");
        Cookie.write("serverAddress", server);
    }

    var isLogin = window.localStorage ? localStorage.getItem("isLogin") : Cookie.read("isLogin");

    if (!(isLogin == "login")){
        if (window.localStorage) {
            console.log("localStorage ");
            localStorage.setItem("isLogin", "offline");
        } else {
            console.log("cookie");
            Cookie.write("isLogin", "offline");
        }
    } else {
        if (window.localStorage) {
            console.log("localStorage ", "login");
            localStorage.setItem("isLogin", "login");
        } else {
            console.log("cookie");
            Cookie.write("isLogin", "login");
        }
        document.getElementById('upName').innerHTML = window.localStorage ? localStorage.getItem("username") : Cookie.read("username");
    }

    layer.ready(function () {

    });

}]);
app.provider('myCSRF', [function () {
    var headerName = 'X-CSRFToken';
    var cookieName = 'csrftoken';
    var allowedMethods = ['GET'];

    this.setHeaderName = function (n) {
        headerName = n;
    }
    this.setCookieName = function (n) {
        cookieName = n;
    }
    this.setAllowedMethods = function (n) {
        allowedMethods = n;
    }
    this.$get = ['$cookies', function ($cookies) {
        return {
            'request': function (config) {
                if (allowedMethods.indexOf(config.method) === -1) {
                    // do something on success
                    config.headers[headerName] = $cookies[cookieName];
                    console.log($cookies.csrftoken);
                }
                return config;
            }
        }
    }];
}]).config(function ($httpProvider) {
    $httpProvider.interceptors.push('myCSRF');
});

