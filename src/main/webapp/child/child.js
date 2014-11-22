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
            .when('/child/class/:yearClassId/list', {
                templateUrl: 'child/list.html',
                controller: 'listChildController'
            })
    }])

    .factory('Child', ['$resource', function($resource) {
        return $resource('api/child/:id', { }, {
            yearClass: {method: 'GET', url: 'api/child/class/:yearClassId', isArray: true},
            save: {method: 'PUT', url: 'api/child'},
            saveWithAddress: {method: 'PUT', url: 'api/child/address'}
        });
    }])

    .service('ChildService', [function () {
        var child, childIds;
    }])

    .controller('listChildController', ['$scope', '$routeParams', 'Child', 'ChildService',
        function ($scope, $routeParams, Child, ChildService) {
            angular.extend($scope, {
                data: {
                    children: Child.yearClass({yearClassId: $routeParams.yearClassId})
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
                ChildService.childIds = children.map(function (child) {
                    return child.id;
                });
                $scope.viewData.noChildren = children.length == 0;
            });

        }
    ])

    .controller('createChildController', ['$scope', 'Flash', 'Child', 'ChildService', 'SchoolService', 'Address',  'uuid4',
        function ($scope, Flash, Child, ChildService, SchoolService, Address, uuid4) {
            angular.extend($scope, {
                data: {
                    child: {
                        id: uuid4.generate(),
                        yearClassId: SchoolService.yearClassId
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
                    ChildService.childIds = [];
                    ChildService.child = $scope.data.child;
                    Flash.setSuccessMessage("Επιτυχής καταχώρηση.");
                    $scope.toScopedChild();
                }, function (response) {
                    Flash.setWarningMessage("Σφάλμα καταχώρησης.");
                });
            }
        }])

    .controller('updateChildController', ['$scope', '$routeParams', '$window', '$location', '$modal', 'Flash', 'Child',
                'ChildService', 'SchoolService', 'Address', 'Relationship',
        function ($scope, $routeParams, $window, $location, $modal, Flash, Child,
                ChildService, SchoolService, Address, Relationship) {

            angular.extend($scope, {
                data: {
                    child: Child.get({id: $routeParams.childId}),
                    address: null,
                    relationships: Relationship.fetchRelationships({childId: $routeParams.childId})
                },
                viewData: {
                    submitLabel: 'Ανανέωση',
                    hasChildrenIdsInScope: ChildService.childIds.length > 1
                }
            });

            $scope.data.child.$promise.then(function (response) {
                ChildService.child = $scope.data.child;
                $scope.data.address = Address.get({addressId: $scope.data.child.addressId});
            });

            $scope.submit = function () {
                var childWithAddress = {
                    child: $scope.data.child,
                    address: $scope.data.address
                }

                Child.saveWithAddress(childWithAddress).$promise.then(function (response) {
                    ChildService.child = $scope.data.child;
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
                var numberOfChildren = ChildService.childIds.length;
                var currentChildId = ChildService.child.id;
                var currentChildIdIndex = ChildService.childIds.indexOf(currentChildId);
                if (currentChildIdIndex + 1 < numberOfChildren) {
                    result.id = ChildService.childIds[currentChildIdIndex + 1];
                } else {
                    result.id = ChildService.childIds[0];
                    result.rollover = true;
                }
                return result;
            }

            function findPreviousChild() {
                var result = {};
                var numberOfChildren = ChildService.childIds.length;
                var currentChildId = ChildService.child.id;
                var currentChildIdIndex = ChildService.childIds.indexOf(currentChildId);
                if (currentChildIdIndex != 0) {
                    result.id = ChildService.childIds[currentChildIdIndex - 1];
                } else {
                    result.id = ChildService.childIds[numberOfChildren - 1];
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
                            return ChildService.child.id;
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

    .controller('removeChildModalController', ['$scope', '$modalInstance', 'Child', 'childId', 'ChildService',
        function ($scope, $modalInstance, Child, childId, ChildService) {
            $scope.removeChild = function () {
                Child.remove({id: childId}).$promise.then(function (response) {
                    ChildService.child = null;
                    $scope.dismiss();
                    $scope.toChildList();
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }])

    .controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'ChildService', 'Relationship', 'relationshipId',
        function ($scope, $modalInstance, ChildService, Relationship, relationshipId) {
            $scope.removeRelationship = function () {
                Relationship.remove({id: relationshipId}).$promise.then(function (response) {
                    return Relationship.fetchRelationships({childId: ChildService.child.id}).$promise;
                }).then(function (response) {
                    $scope.dismiss();
                    $scope.data.relationships = response;
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }]);
