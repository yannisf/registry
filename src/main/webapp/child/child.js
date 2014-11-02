'use strict';

angular.module('child', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4'])

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
        return $resource('api/child/:childId', {childId: '@id'}, {
            //custom actions here.
        });
    }])

    .service('childService', ['$http', 'Child', function ($http, Child) {
        return {
            update: function (child) {
                return $http.put('api/child/', child).then(function (response) {
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

    .controller('listChildController', ['$scope', 'Child', 'childService', 'statefulChildService',
        function ($scope, Child, childService, statefulChildService) {
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

            $scope.data.children.$promise.then(function(newval) {
                var childIds = newval.map(function (child) {
                    return child.id;
                });
                statefulChildService.setChildIds(childIds);
                $scope.viewData.noChildren = newval.length == 0;
            });

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

    .controller('updateChildController', ['$scope', '$window', '$location', '$modal', 'Flash', 'Child', 'childService', 'statefulChildService', 'Address', 'addressService',
        function ($scope, $window, $location, $modal, Flash, Child, childService, statefulChildService, Address, addressService) {
            angular.extend($scope, {
                data: {
                    child: Child.get({childId: statefulChildService.getScopedChildId()}),
                    address: null,
                    relationships: []
                },
                viewData: {
                    submitLabel: 'Ανανέωση'
                }
            });

            $scope.data.child.$promise.then(function (response) {
                $scope.data.address = Address.get({addressId: $scope.data.child.addressId});
                statefulChildService.setScopedChildAddressId($scope.data.address.id);
            });

            childService.fetchRelationships(statefulChildService.getScopedChildId()).then(function (response) {
                $scope.data.relationships = response;
            });

            $scope.submit = function () {
                addressService.update($scope.data.address).then(function (response) {
                    return childService.update($scope.data.child);
                }).then(function (response) {
                    Flash.setSuccessMessage("Επιτυχής καταχώρηση. ");
                    $scope.toScopedChild();
                }, function (errorResponse) {
                    Flash.setWarningMessage("Σφάλμα καταχώρησης. ");
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

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

//            angular.element($window).on('keyup', function(e) {
//                if(e.keyCode == '39') { //RIGHT ARROW
//                    $scope.nextChild();
//                } else if (e.keyCode == '37') { //LEFT ARROW
//                    $scope.previousChild();
//                } else if (e.keyCode == '46') { //DEL
//                    $scope.confirmRemoveChild();
//                }
//                $scope.$apply();
//            });
//
//            $scope.$on('$locationChangeSuccess', function () {
//                angular.element($window).off('keyup');
//            });

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
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                Child.remove({childId: childId}).$promise.then(function (response) {
                    $scope.dismiss();
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
                    $scope.dismiss();
                    $scope.data.relationships = response;
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }]);
