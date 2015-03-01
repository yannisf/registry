'use strict';
angular.module('foundation')

    .directive('breadcrumb', ['FoundationService', 'ChildService', function (FoundationService, ChildService) {
        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "application/foundation/breadcrumb.tpl.html",
            link: function(scope) {
                scope.school = FoundationService.school;
                scope.classroom = FoundationService.classroom;
                scope.term = FoundationService.term;
                scope.group = FoundationService.group;
                scope.child = ChildService.cache.child;
            }
        };
    }]);
