'use strict';

angular.module('child', ['ngRoute', 'ui.bootstrap', 'uuid4'])

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
                return $http.get('api/child/' + childId).then(function (response) {
                    return response.data;
                });
            },
            fetchAll: function () {
                return $http.get('api/child/all').then(function (response) {
                    return response.data;
                });
            },
            create: function (child) {
                return $http.post('api/child', child).then(function (response) {
                    return response.data;
                });
            },
            update: function (childId, child) {
                return $http.post('api/child/' + childId, child).then(function (response) {
                    return response.data;
                });
            },
            remove: function (childId) {
                return $http({method: 'DELETE', url: 'api/child/' + childId}).then(function (response) {
                    return response.data;
                });
            },
            fetchRelationships: function (childId) {
                return $http.get('api/relationship/child/' + childId + '/guardian').then(function (response) {
                    return response.data;
                });
            },
            removeRelationship: function (relationshipId) {
                return $http({method: 'DELETE', url: 'api/relationship/' + relationshipId}).then(function (response) {
                    return response.data;
                });
            }
        };
    }])

    .service('statefulChildService', ['$routeParams', function ($routeParams) {
        var childId;
        var childAddressId;

        return {
            getScopedChildId: function () {
                if ($routeParams.childId) {
                    childId = $routeParams.childId;
                }

                return childId;
            },
            setScopedChildId: function (value) {
                childId = value;
            },
            getScopedChildAddressId: function () {
                return childAddressId;
            },
            setScopedChildAddressId: function (value) {
                childAddressId = value;
            }
        }
    }])

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

    .controller('createChildController', ['$scope', 'childService', 'statefulChildService', 'addressService', 'uuid4',
        function ($scope, childService, statefulChildService, addressService, uuid4) {
            angular.extend($scope, {
                data: {
                    child: null,
                    address: {
                        id: uuid4.generate()
                    }
                },
                viewData: {
                    submitLabel: 'Εισαγωγή'
                }
            });

            $scope.submit = function () {
                addressService.update($scope.data.address).then(function (response) {
                    $scope.data.child.addressId = $scope.data.address.id;
                    return childService.create($scope.data.child);
                }).then(function (response) {
                    var childId = response.id;
                    statefulChildService.setScopedChildId(childId);
                    statefulChildService.setScopedChildAddressId($scope.data.child.addressId)
                    $scope.toScopedChild();
                });
            }
        }])

    .controller('updateChildController', ['$scope', '$location', '$modal', 'childService', 'statefulChildService', 'addressService',
        function ($scope, $location, $modal, childService, statefulChildService, addressService) {
            angular.extend($scope, {
                data: {
                    child: null,
                    address: null,
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
                    return addressService.fetch($scope.data.child.addressId);
                }).then(function (response) {
                    $scope.data.address = response;
                    statefulChildService.setScopedChildAddressId($scope.data.address.id);
                });

            childService.fetchRelationships($scope.viewData.childId).then(
                function (response) {
                    $scope.data.relationships = response;
                }
            );

            $scope.submit = function () {
                addressService.update($scope.data.address).then(function (response) {
                    return childService.update($scope.viewData.childId, $scope.data.child);
                }).then(function (response) {
                    $scope.toScopedChild();
                });
            };

            $scope.addGuardian = function () {
                $location.url('/guardian/edit');
            };

            $scope.confirmRemoveChild = function () {
                $modal.open({
                    templateUrl: 'child/remove-child.tpl.html',
                    controller: 'removeChildModalController',
                    resolve: {
                        childId: function () {
                            return $scope.viewData.childId;
                        }
                    }
                });
            };

            $scope.confirmRemoveRelationship = function (relationshipId) {
                $modal.open({
                    templateUrl: 'child/remove-guardian.tpl.html',
                    controller: 'removeRelationshipModalController',
                    scope: $scope,
                    resolve: {
                        relationshipId: function () {
                            return relationshipId;
                        }
                    }
                });
            };
        }])

    .controller('removeChildModalController', ['$scope', '$modalInstance', 'childService', 'childId',
        function ($scope, $modalInstance, childService, childId) {
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
        }])

    .controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'childService', 'relationshipId',
        function ($scope, $modalInstance, childService, relationshipId) {
            $scope.removeRelationship = function () {
                childService.removeRelationship(relationshipId).then(function (response) {
                    return childService.fetchRelationships($scope.viewData.childId);
                }).then(function (response) {
                    $scope.data.relationships = response;
                    $scope.dismiss();
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }]);
