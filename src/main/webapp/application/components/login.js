'use strict';
angular.module('schoolApp')
    .directive('login', ['$rootScope', '$http', '$window', '$location', function ($rootScope, $http, $window, $location) {
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
            controller: ['$scope', function($scope) {
                $scope.login = function() {
                    $http({
                        method: 'POST',
                        url: '/registry/api/login',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        transformRequest: function(parameters) {
                            var str = [];
                            for (var p in parameters) {
                                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(parameters[p]));
                            }
                            return str.join("&");
                        },
                        data: { username: $scope.data.username, password: $scope.data.password, remember: $scope.data.remember }
                    }).success(function(data, status, headers, config) {

                        $http.get('api/context/authentication').success(function(data) {
                            $rootScope.credentials = {
                                authenticated: data.name != 'anonymousUser',
                                invalid: false,
                                username: data.name,
                                authorities: data.authorities
                            };

                            $location.path('/');
                        });

                    }).error(function(data, status, headers, config) {
                        console.log('BAD CREDENTIALS');
                        $rootScope.credentials.invalid = true;
                    });

                    $scope.data.username = null;
                    $scope.data.password = null;
                    $scope.data.remember = null;
                };
            }]
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