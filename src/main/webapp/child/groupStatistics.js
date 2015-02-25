'use strict';
angular.module('child')

    .directive('groupStatistics', ['School', function (School) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                schoolData: "="
            },
            templateUrl: "child/groupStatistics.tpl.html",
            link: function(scope, element) {
                scope.schoolData.$promise.then(function() {
                    scope.groupStatistics = School.groupStatistics({groupId: scope.schoolData.id});
                });
            }
        };
    }]);
