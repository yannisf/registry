'use strict';

angular.module('management')

    .directive('groups', [function () {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/groups.tpl.html"
		};
	}]);