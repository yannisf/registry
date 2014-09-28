'use strict';

angular.module('schoolApp', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/child/create', {
                templateUrl: 'child/edit.html',
                controller: 'createChildController'
            })
            .when('/child/update', {
                templateUrl: 'child/edit.html',
                controller: 'updateChildController'
            })
            .when('/child/view', {
                templateUrl: 'child/view.html',
                controller: 'updateChildController'
            })
            .when('/child/list', {
                templateUrl: 'child/list.html',
                controller: 'ChildController'
            })
            .otherwise({
                redirectTo: '/child/list'
            });
    }])

    .service('childService', ['$http', function ($http) {
        return {
            fetch: function (childId) {
                return $http.get('api/child/' + childId).then(
                    function (response) {
                        return response.data;
                    });
            },
            fetchAll: function () {
                return $http.get('api/child/all').then(
                    function (response) {
                        return response.data;
                    });
            },
            create: function (child) {
                return $http.post('api/child', child).then(
                    function (response) {
                        return response.data;
                    });
            },
            update: function (childId, child) {
                return $http.post('api/child/' + childId, child).then(
                    function (response) {
                        return response.data;
                    });
            },
            remove: function (childId) {
                return $http({method: 'DELETE', url: 'api/child/' + childId}).then(
                    function (response) {
                        return response.data;
                    });
            }
        };
    }])

    .controller('ChildController', ['$scope', 'childService', function ($scope, childService) {
        childService.fetchAll().then(
            function (data) {
                $scope.children = data;
            }
        );
    }])

    .controller('createChildController', ['$scope', '$location', 'childService',
        function ($scope, $location, childService) {
            $scope.data = {
                child: null
            };

            $scope.cancel = function () {
                $location.url('/child/list');
            };

            $scope.submit = function () {
                childService.create($scope.data.child).then(
                    function (response) {
                        $location.url('/child/list');
                    }
                );
            }
        }])

    .controller('updateChildController', ['$scope', '$routeParams', '$location', 'childService',
        function ($scope, $routeParams, $location, childService) {
            $scope.data = {
                child: null
            };

            $scope.viewData = {
                childId: $routeParams.id
            }

            childService.fetch($scope.viewData.childId).then(
                function (response) {
                    $scope.data.child = response;
                }
            );

            $scope.submit = function () {
                childService.update($scope.viewData.childId, $scope.data.child).then(
                    function (response) {
                        $location.url('/child/list');
                    }
                );
            };

            $scope.remove = function () {
                console.log('Invoked remove');
                childService.remove($scope.viewData.childId).then(
                    function (response) {
                        $location.url('/child/list');
                    }
                );
            };

            $scope.cancel = function () {
                $location.url('/child/list');
            };

        }]);
