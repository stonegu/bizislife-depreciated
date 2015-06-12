'use strict';

(function () {
	var signupControllerModle = angular.module('signupControllerModle', []);
	
	signupControllerModle.controller('signupController', ['$scope', 'ngFabForm', function($scope, ngFabForm) {
		$scope.signupform = {};
		
        $scope.defaultFormOptions = ngFabForm.config;
        $scope.customFormOptions = angular.copy(ngFabForm.config);		
		
		$scope.signup = function() {
			console.log(JSON.stringify($scope.signupform));
		}
		
	}]);
})();


