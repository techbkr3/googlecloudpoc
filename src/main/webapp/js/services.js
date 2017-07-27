'use strict';

/* Services */
var importBillsServices = angular.module('importBillsServices', [ 'ngResource' ]);

importBillsServices.factory('LoginService',['$http','$location','$rootScope', function($http, $location, $rootScope) {
	var service = {};
	service.Login = function(username, password) {
		return $http.post('webapi/security/authenticate', {
			userName : username,
			password : password
		});
	};
	service.isLoggedIn = function() {
		$http.defaults.headers.common.authToken = sessionStorage.authToken;
		return $http.post('webapi/security/isLoggedIn');
	};
	return service;
}]);

importBillsServices.factory('User', [function() {
	var sdo = {
		isLogged: false,
		username: '',
		password: ''
	};
	return sdo;
}]);
