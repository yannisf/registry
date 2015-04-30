'use strict';
angular.module('overview').directive('groupStatistics', ['ActiveCache', 'Group', function (ActiveCache, Group) {
    return {
        restrict: 'E',
        replace: true,
        scope: {groupId: '='},
        templateUrl: "application/components/statistics.tpl.html",
        bindToController: true,
        controllerAs: 'ctrl',
        controller: function() {
            this.statistics = Group.statistics({id: this.groupId});
        }
    };
}]);
