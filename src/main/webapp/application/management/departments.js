'use strict';

angular.module('management')

    .directive('departments', [function () {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/departments.tpl.html"
		};
	}]);