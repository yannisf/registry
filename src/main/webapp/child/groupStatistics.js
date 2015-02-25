'use strict';
angular.module('child')

    .directive('groupStatistics', ['School', function (School) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                groupId: "="
            },
            templateUrl: "child/groupStatistics.tpl.html",
            link: function(scope, element) {
                scope.groupStatistics = School.groupStatistics({groupId: scope.groupId});
            }
        };
    }]);
