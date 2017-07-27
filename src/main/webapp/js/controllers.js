'use strict';

/* Controllers */
var importBillsControllers = angular.module('importBillsControllers', []);

importBillsControllers.controller('LoginController', [ '$scope',
		function($scope) {
			$scope.var1 = "Test scope";
		} ]);

importBillsControllers.controller('loginCtrl', [ '$scope', '$http', 'User', 'LoginService',
	function($scope, $http, User, LoginService) {
		$scope.User = User;

		$scope.login = function() {
			LoginService.Login($scope.User.username, $scope.User.password).success(function(data, status, headers, config) {
				console.log(data);
				$scope.authResult = data.authResult;
			});
		}

		$scope.logout = function() {
			var url = "php/ReadCookie.php?action=delete";
            $http.get(url).success(function (response) {
                User.password = "";
                User.isLogged = response[0].loggedin;
                User.username = response[0].username;
                $scope.User = User;
            });
			User.isLogged = "false";
			User.username = "";
			User.password = "";
			$scope.User = User;
			$("#adminMenu").hide();
		}

	} ]);
