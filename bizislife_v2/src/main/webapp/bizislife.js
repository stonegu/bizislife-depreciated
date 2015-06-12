/*! StoneGu - v1.0.0 - 2015-06-11
* Copyright (c) 2015 ; Licensed  */
/*!
 * Start Bootstrap - Agency Bootstrap Theme (http://startbootstrap.com)
 * Code licensed under the Apache License v2.0.
 * For details, see http://www.apache.org/licenses/LICENSE-2.0.
 */

// jQuery for page scrolling feature - requires jQuery Easing plugin
$(function() {
    $('a.page-scroll').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });
    
});

// Highlight the top nav as scrolling occurs
$('body').scrollspy({
    target: '.navbar-fixed-top'
})

// Closes the Responsive Menu on Menu Item Click
$('.navbar-collapse ul li a').click(function() {
    $('.navbar-toggle:visible').click();
});;'use strict';

(function () {
	var bizApp = angular.module('bizApp', ['ngFabForm', 'ngMessages', 'ngAnimate', 'signupControllerModle']);
	bizApp.config(['ngFabFormProvider', function(ngFabFormProvider) {
        ngFabFormProvider.extendConfig({
            setAsteriskForRequiredLabel: true
        });		
	}])
})();


;'use strict';

(function () {
	var bizAppService = angular.module('bizAppService', []);
	
})();


;'use strict';

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


