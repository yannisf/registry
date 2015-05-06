'use strict';
angular.module('schoolApp')
    .directive('logout', ['$rootScope', '$http', '$window', function ($rootScope, $http, $window) {
        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "application/components/logout.tpl.html",
            controller: ['$scope', function($scope) {
                $scope.logout = function() {
                    $http.post('/registry/api/logout').success(
                        function(data) {
                            $window.location.replace($rootScope.contextPath);
                        }
                    );
                };
            }]
        };
    }]);
