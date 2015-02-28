'use strict';
angular.module('schoolApp')

    .directive('navbar', ['FoundationService', 'ChildService', function (FoundationService, ChildService) {
        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "navbar/navbar.tpl.html",
            link: function(scope, element) {
                scope.group = FoundationService.group;
            }
        };
    }]);
