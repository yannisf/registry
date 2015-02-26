'use strict';
angular.module('child')

    .directive('groupStatistics', ['Foundation', function (Foundation) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                groupId: "="
            },
            templateUrl: "child/groupStatistics.tpl.html",
            link: function(scope, element) {
                scope.groupStatistics = Foundation.groupStatistics({groupId: scope.groupId});
            }
        };
    }]);
