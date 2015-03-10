'use strict';

angular.module('management')

    .directive('terms', [function () {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/terms.tpl.html"
		};
	}]);