'use strict';
angular.module('management').directive('groupStatistics', ['Group', function (Group) {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            groupId: "="
        },
        templateUrl: "application/components/statistics.tpl.html",
        link: function(scope, element) {
            scope.groupStatistics = Group.statistics({id: scope.groupId});
        }
    };
}]);
