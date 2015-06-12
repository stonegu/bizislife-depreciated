'use strict';

(function () {
	var bizApp = angular.module('bizApp', ['ngFabForm', 'ngMessages', 'ngAnimate', 'signupControllerModle']);
	bizApp.config(['ngFabFormProvider', function(ngFabFormProvider) {
        ngFabFormProvider.extendConfig({
            setAsteriskForRequiredLabel: true
        });		
	}])
})();
