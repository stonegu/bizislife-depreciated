/*! StoneGu - v1.0.0 - 2015-07-13
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
	var bizApp = angular.module('bizApp', ['ngFabForm', 'ngMessages', 'ngAnimate', 
	                                       'signupControllerModle',
	                                       'menuControllerModle']);
	
	bizApp.config(['ngFabFormProvider', function(ngFabFormProvider) {
        ngFabFormProvider.extendConfig({
            setAsteriskForRequiredLabel: true
        });		
	}])
})();
;'use strict';

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


;'use strict';

(function () {
	var menuControllerModle = angular.module('menuControllerModle', ['ui.bootstrap', 'bizAppService']);
	
	menuControllerModle.controller('menuController', ['$scope', '$log', function($scope, $log) {
		
	}]);
})();


;'use strict';

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


