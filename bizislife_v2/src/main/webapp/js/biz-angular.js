'use strict';

(function () {
	var bizApp = angular.module('bizApp', ['ngFabForm', 'ngMessages', 'ngAnimate', 
	                                       'signupControllerModle',
	                                       'menuControllerModle']);
	
	bizApp.config(['ngFabFormProvider', function(ngFabFormProvider) {
        ngFabFormProvider.extendConfig({
            setAsteriskForRequiredLabel: true
        });		
	}])
})();
