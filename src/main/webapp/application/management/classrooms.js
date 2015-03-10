'use strict';

angular.module('management')

    .directive('classrooms', [function () {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/classrooms.tpl.html"
		};
	}]);