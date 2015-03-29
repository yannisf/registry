'use strict';
angular.module('schoolApp').directive('navbar', ['$location', 'ActiveCache', function ($location, ActiveCache) {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        templateUrl: "application/components/navbar.tpl.html",
        controller: function($scope) {
            $scope.active = ActiveCache;
            
            $scope.toGroup = function() {
                $location.url('/group/' + ActiveCache.group.id);
            };
        }
    };
}]);
