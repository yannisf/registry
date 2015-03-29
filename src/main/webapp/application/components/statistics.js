'use strict';
angular.module('overview').directive('groupStatistics', ['ActiveCache', 'Group', function (ActiveCache, Group) {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        templateUrl: "application/components/statistics.tpl.html",
        link: function(scope, element) {
            scope.statistics = Group.statistics({id: ActiveCache.group.id});
        }
    };
}]);
