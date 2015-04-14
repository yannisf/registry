'use strict';
angular.module('schoolApp')
    .directive('login', ['$http', function ($http) {
        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "application/components/login.tpl.html",
            link: function(scope, element) {
                element.find('input').bind('click', function(event) {
                    event.stopPropagation();
                });
            },
            controller: function($scope) {
                $scope.login = function() {
                    $http.post('login', {
                        username: $scope.data.username,
                        password: $scope.data.password
                    }).success(
                        function(data, status, headers, config) {
                            console.log('data: ', data);
                            console.log('status: ', status);
                            console.log('headers: ', headers);
                    });
                    $scope.data.username = null;
                    $scope.data.password = null;
                    $scope.data.persist = null;
                };
            }
        };
    }])

    .directive('focusUsername', [function () {
        return {
            restrict: 'A',
            link: function(scope, element) {
                element.bind('click', function(event) {
                    element.find('input')[0].focus();
                });
            }
        };
    }]);