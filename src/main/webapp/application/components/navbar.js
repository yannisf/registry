'use strict';
angular.module('schoolApp').directive('navbar', ['ActiveCache', function (ActiveCache) {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        templateUrl: "application/components/navbar.tpl.html",
        controller: function($scope) {
            $scope.group = ActiveCache.group;
        }
    };
}]);
