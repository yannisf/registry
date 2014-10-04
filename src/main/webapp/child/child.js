'use strict';

angular.module('child', ['ngRoute', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/child/create', {
                templateUrl: 'child/edit.html',
                controller: 'createChildController'
            })
            .when('/child/:childId/update', {
                templateUrl: 'child/edit.html',
                controller: 'updateChildController'
            })
            .when('/child/:childId/view', {
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
            },
            fetchRelationships: function (childId) {
                return $http.get('api/child/' + childId + '/guardian').then(
                    function (response) {
                        return response.data;
                    });
            },

        };
    }])

    .service('statefulChildService', ['$routeParams', function($routeParams) {
        var childId;

        return {
            getScopedChildId: function() {
                if ($routeParams.childId) {
                    childId = $routeParams.childId;
                }

                return childId;
            },
            setScopedChildId: function(value) {
                childId = value;
            }
        }
    }])

    .directive('displayChild', function() {
        return {
          restrict: 'E',
          scope: {
            child: "=child"
          },
          templateUrl: "child/display-tpl.html"
        };
    })

    .controller('ChildController', ['$scope', 'childService', function ($scope, childService) {
        angular.extend($scope, {
            data: {
                children: []
            },
            viewData: {
                noChildren: true
            }
        });

        childService.fetchAll().then(
            function (data) {
                if (data.length > 0) {
                    $scope.data.children = data;
                    $scope.viewData.noChildren = false;
                }
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
                    submitLabel: 'Εισαγωγή'
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
                    child: null,
                    relationships: []
                },
                viewData: {
                    childId: statefulChildService.getScopedChildId(),
                    submitLabel: 'Ανανέωση'
                }
            });

            childService.fetch($scope.viewData.childId).then(
                function (response) {
                    $scope.data.child = response;
                }
            );

            childService.fetchRelationships($scope.viewData.childId).then(
                function (response) {
                    $scope.data.relationships = response;
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
