'use strict';

angular.module('child', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4', 'relationship'])

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

    .factory('Child', ['$resource', function($resource) {
        return $resource('api/child/:id', { }, {
            save: {method: 'PUT', url: 'api/child'},
            saveWithAddress: {method: 'PUT', url: 'api/child/address'}
        });
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

    .controller('listChildController', ['$scope', 'Child', 'statefulChildService',
        function ($scope, Child, statefulChildService) {
            angular.extend($scope, {
                data: {
                    children: Child.query()
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

            $scope.data.children.$promise.then(function(children) {
                var childIds = children.map(function (child) {
                    return child.id;
                });
                statefulChildService.setChildIds(childIds);
                $scope.viewData.noChildren = children.length == 0;
            });

        }
    ])

    .controller('createChildController', ['$scope', 'Flash', 'Child', 'statefulChildService', 'Address',  'uuid4',
        function ($scope, Flash, Child, statefulChildService, Address, uuid4) {
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
                var childWithAddress = {
                    child: $scope.data.child,
                    address: $scope.data.address
                }

                Child.saveWithAddress(childWithAddress).$promise.then(function (response) {
                    statefulChildService.setScopedChildId($scope.data.child.id);
                    statefulChildService.setScopedChildAddressId($scope.data.address.id);
                    Flash.setSuccessMessage("Επιτυχής καταχώρηση.");
                    $scope.toScopedChild();
                }, function (response) {
                    Flash.setWarningMessage("Σφάλμα καταχώρησης.");
                });
            }
        }])

    .controller('updateChildController', ['$scope', '$window', '$location', '$modal', 'Flash', 'Child', 'statefulChildService', 'Address', 'Relationship',
        function ($scope, $window, $location, $modal, Flash, Child, statefulChildService, Address, Relationship) {
            angular.extend($scope, {
                data: {
                    child: Child.get({id: statefulChildService.getScopedChildId()}),
                    address: null,
                    relationships: Relationship.fetchRelationships({childId: statefulChildService.getScopedChildId()})
                },
                viewData: {
                    submitLabel: 'Ανανέωση'
                }
            });

            $scope.data.child.$promise.then(function (response) {
                $scope.data.address = Address.get({addressId: $scope.data.child.addressId});
                statefulChildService.setScopedChildAddressId($scope.data.child.addressId);
            });

            $scope.submit = function () {
                var childWithAddress = {
                    child: $scope.data.child,
                    address: $scope.data.address
                }

                Child.saveWithAddress(childWithAddress).$promise.then(function (response) {
                    statefulChildService.setScopedChildId($scope.data.child.id);
                    statefulChildService.setScopedChildAddressId($scope.data.address.id);
                    Flash.setSuccessMessage("Επιτυχής καταχώρηση.");
                    $scope.toScopedChild();
                }, function (response) {
                    Flash.setWarningMessage("Σφάλμα καταχώρησης.");
                });
            }

            $scope.addGuardian = function () {
                $location.path('/guardian/edit');
            };

            $scope.goToGuardian = function ($event) {
                var clickedElement = angular.element($event.target);
                var guardianId = clickedElement.scope().relationship.guardian.id;
                $scope.go('/guardian/' + guardianId + '/view', $event);
            };

            $scope.nextChild = function () {
                var next = findNextChild();
                if (next.rollover) {
                    Flash.setMessage("Ανακύκλωση καταλόγου. ");
                }
                $location.url('/child/' + next.id + '/view');
            };

            $scope.previousChild = function () {
                var previous = findPreviousChild();
                if (previous.rollover) {
                    Flash.setMessage("Ανακύκλωση καταλόγου. ");
                }
                $location.url('/child/' + previous.id + '/view');
            };

            function findNextChild() {
                var result = {};
                var numberOfChildren = statefulChildService.getChildIds().length;
                var currentChildId = statefulChildService.getScopedChildId();
                var currentChildIdIndex = statefulChildService.getChildIds().indexOf(currentChildId);
                if (currentChildIdIndex + 1 < numberOfChildren) {
                    result.id = statefulChildService.getChildIds()[currentChildIdIndex + 1];
                } else {
                    result.id = statefulChildService.getChildIds()[0];
                    result.rollover = true;
                }
                return result;
            }

            function findPreviousChild() {
                var result = {};
                var numberOfChildren = statefulChildService.getChildIds().length;
                var currentChildId = statefulChildService.getScopedChildId();
                var currentChildIdIndex = statefulChildService.getChildIds().indexOf(currentChildId);
                if (currentChildIdIndex != 0) {
                    result.id = statefulChildService.getChildIds()[currentChildIdIndex - 1];
                } else {
                    result.id = statefulChildService.getChildIds()[numberOfChildren - 1];
                    result.rollover = true;
                }
                return result;
            }

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

    .controller('removeChildModalController', ['$scope', '$modalInstance', 'Child', 'childId',
        function ($scope, $modalInstance, Child, childId) {
            $scope.removeChild = function () {
                Child.remove({id: childId}).$promise.then(function (response) {
                    $scope.dismiss();
                    $scope.toChildList();
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }])

    .controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'statefulChildService', 'Relationship', 'relationshipId',
        function ($scope, $modalInstance, statefulChildService, Relationship, relationshipId) {
            $scope.removeRelationship = function () {
                Relationship.remove({id: relationshipId}).$promise.then(function (response) {
                    return Relationship.fetchRelationships({childId: statefulChildService.getScopedChildId()}).$promise;
                }).then(function (response) {
                    $scope.dismiss();
                    $scope.data.relationships = response;
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }]);
