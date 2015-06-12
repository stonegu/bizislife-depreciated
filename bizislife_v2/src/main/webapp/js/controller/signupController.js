'use strict';

(function () {
	var signupControllerModle = angular.module('signupControllerModle', ['bizAppService']);
	
	signupControllerModle.controller('signupController', ['$scope', 'ngFabForm', 'signupFactory', function($scope, ngFabForm, signupFactory) {
		$scope.signupform = {};
		
        $scope.defaultFormOptions = ngFabForm.config;
        $scope.customFormOptions = angular.copy(ngFabForm.config);		
		
		$scope.signup = function() {
			console.log(JSON.stringify($scope.signupform));
			
			signupFactory.signup($scope.signupform).then(function(response) {
				console.log('response: ' + JSON.stringify(response.data));
			}, function(error) {
				console.log(error);
			});
			
		}
		
	}]);
})();


