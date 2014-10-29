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
                controller: 'listChildController'
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
            update: function (child) {
                return $http.put('api/child/', child).then(function (response) {
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
        var childIds;
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
            },
            getChildIds: function () {
                return childIds;
            },
            setChildIds: function (value) {
                childIds = value;
            }
        }
    }])

    .controller('listChildController', ['$scope', 'childService', 'statefulChildService',
        function ($scope, childService, statefulChildService) {
            angular.extend($scope, {
                data: {
                    children: []
                },
                viewData: {
                    noChildren: true
                }
            });

            $scope.goToChild = function ($event) {
                var clickedElement = angular.element($event.target);
                var childId = clickedElement.scope().child.id;
                $scope.go('/child/' + childId + '/view', $event);
            };

            childService.fetchAll().then(function (data) {
                $scope.data.children = data;
            });

            $scope.$watch('data.children', function (newval) {
                var childIds = newval.map(function (child) {
                    return child.id.toString();
                });
                statefulChildService.setChildIds(childIds);
                $scope.viewData.noChildren = newval.length == 0;
            })

        }
    ])

    .controller('createChildController', ['$scope', 'childService', 'statefulChildService', 'addressService', 'uuid4',
        function ($scope, childService, statefulChildService, addressService, uuid4) {
            angular.extend($scope, {
                data: {
                    child: {
                        id: uuid4.generate()
                    },
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
                    return childService.update($scope.data.child);
                }).then(function (response) {
                    var childId = response.id;
                    statefulChildService.setScopedChildId(childId);
                    statefulChildService.setScopedChildAddressId($scope.data.child.addressId);
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
                    submitLabel: 'Ανανέωση'
                }
            });

            childService.fetch(statefulChildService.getScopedChildId()).then(function (response) {
                $scope.data.child = response;
                return addressService.fetch($scope.data.child.addressId);
            }).then(function (response) {
                $scope.data.address = response;
                statefulChildService.setScopedChildAddressId($scope.data.address.id);
            });

            childService.fetchRelationships(statefulChildService.getScopedChildId()).then(function (response) {
                $scope.data.relationships = response;
            });

            $scope.submit = function () {
                addressService.update($scope.data.address).then(function (response) {
                    return childService.update($scope.data.child);
                }).then(function (response) {
                    $scope.toScopedChild();
                });
            };

            $scope.addGuardian = function () {
                $location.path('/guardian/edit');
            };

            $scope.goToGuardian = function ($event) {
                var clickedElement = angular.element($event.target);
                var guardianId = clickedElement.scope().relationship.guardian.id;
                $scope.go('/guardian/' + guardianId + '/view', $event);
            };

            $scope.confirmRemoveChild = function () {
                $modal.open({
                    templateUrl: 'templates/remove-child.tpl.html',
                    controller: 'removeChildModalController',
                    resolve: {
                        childId: function () {
                            return statefulChildService.getScopedChildId();
                        }
                    }
                });
            };

            $scope.confirmRemoveRelationship = function (relationshipId, $event) {
                $event.stopPropagation();
                $modal.open({
                    templateUrl: 'templates/remove-guardian.tpl.html',
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
                childService.remove(childId).then(function (response) {
                    $modalInstance.dismiss();
                    $scope.toChildList();
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }])

    .controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'childService', 'statefulChildService', 'relationshipId',
        function ($scope, $modalInstance, childService, statefulChildService, relationshipId) {
            $scope.removeRelationship = function () {
                childService.removeRelationship(relationshipId).then(function (response) {
                    return childService.fetchRelationships(statefulChildService.getScopedChildId());
                }).then(function (response) {
                    $modalInstance.dismiss();
                    $scope.data.relationships = response;
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }]);
