'use strict';

(function () {
	var bizAppService = angular.module('bizAppService', []);
	
	bizAppService.factory('signupFactory', ['$http', function($http) {
		return {
			signup: function(signupform) {
				return $http({
                    url: 'sign/signup',
                    method: 'POST',
                    data: signupform
				})
			}
		}
	}]);
	
})();


