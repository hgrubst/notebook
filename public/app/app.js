;(function() {
	'use strict';

	var app = angular.module('app', []);

	app.controller('MainCtrl', ['$scope', '$log', function ($scope, $log) {
		$log.debug('MainCtrl');
	}]);

}());