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
                    $http.post('api/logout').success(
                        function(data) {
                            var location = $window.location.toString();
                            var hashIndex = location.indexOf('#');
                            var locationUrl = location.substring(0, hashIndex);
                            $window.location.replace(locationUrl);
                        }
                    );
                };
            }]
        };
    }]);
