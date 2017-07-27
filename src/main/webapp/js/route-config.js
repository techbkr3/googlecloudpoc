(function(){
	angular.module('routes',['ngRoute'])
	.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'includes/main.html',
		controller : 'LoginController'
	});

	$routeProvider.otherwise({
		redirectTo : '/'
	});
} ])
})();