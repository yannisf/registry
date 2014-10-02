'use strict';

angular.module('child', ['ngRoute', 'ui.bootstrap'])

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

    .service('statefulChildService', ['$routeParams', function($routeParams) {
        var childId;

        return {
            getScopedChildId: function() {
                if ($routeParams.id) {
                    this.childId = $routeParams.id;
                }

                return this.childId;
            },
            setScopedChildId: function(childId) {
                this.childId = childId;
            }
        }
    }])

    .controller('ChildController', ['$scope', 'childService', function ($scope, childService) {
        childService.fetchAll().then(
            function (data) {
                $scope.children = data;
            }
        );
    }])

    .controller('createChildController', ['$scope', 'childService',
        function ($scope, childService) {
            angular.extend($scope, {
                data: {
                    child: null
                },
                viewData: {
                    allowDelete: false,
                    submitLabel: 'Create'
                }
            });

            $scope.submit = function () {
                childService.create($scope.data.child).then(
                    function (response) {
                        $scope.cancel();
                    }
                );
            }
        }])

    .controller('updateChildController', ['$scope', '$location', 'childService', 'statefulChildService',
        function ($scope, $location, childService, statefulChildService) {
            angular.extend($scope, {
                data: {
                    child: null
                },
                viewData: {
                    childId: statefulChildService.getScopedChildId(),
                    allowDelete: true,
                    submitLabel: 'Update'
                }
            });

            childService.fetch($scope.viewData.childId).then(
                function (response) {
                    $scope.data.child = response;
                }
            );

            $scope.submit = function () {
                childService.update($scope.viewData.childId, $scope.data.child).then(
                    function (response) {
                        $scope.cancel();
                    }
                );
            };

            $scope.remove = function () {
                childService.remove($scope.viewData.childId).then(
                    function (response) {
                        $scope.cancel();
                    }
                );
            };

            $scope.addGuardian = function () {
                $location.url('/guardian/edit');
            };

        }]);
