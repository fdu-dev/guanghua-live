'use strict';

// Declare app level module which depends on views, and components
//var server = "http://139.129.10.20:8080/GuangHuaLive";

var app = angular.module('myApp', ['ngCookies', 'ngRoute', 'videoModule', 'headerModule',
    'videoModule', 'commentModule', 'liveIndexModule', 'videoGalleryModule', 'profileModule',
    'modifyPasswordModule','forgetPasswordModule','resetPasswordModule','liveModule']);
app.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $routeProvider
        .when('/app/video/:vid', {
            templateUrl: "template/videoPlayer.tpl.html",
            controller: "VideoCtrl"
        })
        .when('/app/main', {
            templateUrl: "template/liveIndex.tpl.html",
            controller: "LiveIndexCtrl"
        })
        .when('/app/gallery', {
            templateUrl: "template/videoGallery.tpl.html",
            controller: "VideoGalleryCtrl"
        })
        .when('/app/register', {
            templateUrl: "template/register.tpl.html",
            controller: "RegisterCtrl"
        })
        .when('/app/login', {
            templateUrl: "template/login.tpl.html",
            controller: "LoginCtrl"
        })
        .when('/app/profile', {
            templateUrl: "template/profile.tpl.html",
            controller: "ProfileCtrl"
        })
        .when('/app/modifyPassword', {
            templateUrl: "template/modifyPassword.tpl.html",
            controller: "ModifyPasswordCtrl"
        })
        .when('/app/resetPassword/:uid', {
            templateUrl: "template/resetPassword.tpl.html",
            controller: "ResetPasswordCtrl"
        })
        .when('/app/forgetPassword', {
            templateUrl: "template/forgetPassword.tpl.html",
            controller: "ForgetPasswordCtrl"
        })
        .when('/app/live/:vid', {
            templateUrl: "template/livePlayer.tpl.html",
            controller: "LiveCtrl"
        })
        .otherwise({redirectTo: "/app/main"});
}]);
app.run(['$rootScope', '$http', '$cookies', function ($rootScope, $http, $cookies) {
    $rootScope.isLogin = false;
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
//    var server = "http://192.168.1.122:8080/GuangHuaLive/";
    var server = "http://localhost:8080/GuangHuaLive/";
    if (window.localStorage) {
        console.log("localStorage ",server);
        localStorage.setItem("serverAddress", server);
    } else {
        console.log("cookie");
        Cookie.write("serverAddress", server);
    }

    var isLogin = window.localStorage ? localStorage.getItem("isLogin") : Cookie.read("isLogin");
    console.log(isLogin);

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
        $('#centerBtn').show();
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
                }
                return config;
            }
        }
    }];
}]).config(function ($httpProvider) {
    $httpProvider.interceptors.push('myCSRF');
});

