'use strict';

angular.module('management')

    .directive('schools', [function () {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/schools.tpl.html"
		};
	}]);