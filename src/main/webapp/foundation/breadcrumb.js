'use strict';
angular.module('foundation')

    .directive('breadcrumb', ['FoundationService', 'ChildService', function (FoundationService, ChildService) {
        return {
            restrict: 'E',
            replace: true,
            scope: { },
            templateUrl: "foundation/breadcrumb.tpl.html",
            link: function(scope, element) {
                scope.school = FoundationService.school;
                scope.classroom = FoundationService.classroom;
                scope.term = FoundationService.term;
                
                scope.$watch('ChildService.child', )
                scope.child = ChildService.child;
            }
        };
    }]);
