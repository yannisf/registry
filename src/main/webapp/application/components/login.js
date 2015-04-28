'use strict';
angular.module('schoolApp')
    .directive('login', ['$rootScope', '$http', function ($rootScope, $http) {
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

                    var _credentials = {
                        username: $scope.data.username,
                        password: $scope.data.password
                    };

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
                        data: _credentials
                    }).success(
                        function(data, status, headers, config) {
                            console.log('Logged in successfully as ', _credentials.username);
                            $rootScope.credentials.username = _credentials.username;
                        }
                    );

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