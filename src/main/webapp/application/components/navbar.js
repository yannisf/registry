'use strict';
angular.module('schoolApp')
    .directive('navbar', ['$location', '$cookieStore', 'ActiveCache', function ($location, $cookieStore, ActiveCache) {
        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "application/components/navbar.tpl.html",
            controller: function($scope) {
                $scope.active = ActiveCache;
                
                $scope.toOverview = function() {
                    ActiveCache.clearChild();
                    $cookieStore.remove('group');
                    $location.url('/overview');
                };
                
                $scope.toGroup = function() {
                    $location.url('/group/' + ActiveCache.group.id);
                };
            }
        };
    }])
    
    .directive('stopit', [function () {
        return {
            restrict: 'A',
            scope: false,
            link: function(scope, element) {
                element.find('input').bind('click', function(event) {
                    event.stopPropagation();
                });
            }
        };
    }]);
