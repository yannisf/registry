'use strict';
angular.module('foundation')

    .directive('groupStatistics', ['Foundation', function (Foundation) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                groupId: "="
            },
            templateUrl: "application/foundation/groupStatistics.tpl.html",
            link: function(scope, element) {
                scope.groupStatistics = Foundation.groupStatistics({groupId: scope.groupId});
            }
        };
    }]);
