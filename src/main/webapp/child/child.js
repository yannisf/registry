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
                return $http.get('api/relationship/child/' + childId + '/guardian').then(
                    function (response) {
                        return response.data;
                    });
            },
            removeRelationship: function (relationshipId) {
                return $http({method: 'DELETE', url: 'api/relationship/' + relationshipId}).then(
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
                        $scope.toChildList();
                    }
                );
            }
        }])

    .controller('updateChildController', ['$scope', '$location', '$modal', 'childService', 'statefulChildService',
        function ($scope, $location, $modal, childService, statefulChildService) {
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
                        $scope.toChildList();
                    }
                );
            };

            $scope.addGuardian = function () {
                $location.url('/guardian/edit');
            };

            $scope.removeRelationship = function(relationshipId) {
                childService.removeRelationship(relationshipId).then(
                    function(response) {
                        console.log('Removed relationship');
                        childService.fetchRelationships($scope.viewData.childId).then(
                            function (response) {
                                $scope.data.relationships = response;
                            }
                        );
                    }
                )
            };

            $scope.confirmRemoveChild = function () {
                $modal.open({
                    templateUrl: 'removeChildModal',
                    controller: 'removeChildModalController',
                    resolve: {
                        childId: function () {
                            return $scope.viewData.childId;
                        }
                    }
                });
            };

        }])

    .controller('removeChildModalController', ['$scope', '$modalInstance', 'childService', 'childId',
        function($scope, $modalInstance, childService, childId) {
            $scope.removeChild = function () {
                childService.remove(childId).then(
                    function (response) {
                        $scope.dismiss();
                        $scope.toChildList();
                    }
                );
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };

        }]);
