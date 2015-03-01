'use strict';
angular.module('schoolApp')

    .directive('navbar', ['FoundationService', function (FoundationService) {
        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "application/navbar/navbar.tpl.html",
            link: function(scope, element) {
                scope.group = FoundationService.group;
            }
        };
    }]);
