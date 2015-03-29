'use strict';

angular.module('child')

    .controller('updateChildController', ['$scope', '$routeParams', '$window', '$location', '$modal', 'Child',
                'Address', 'RelationshipService', 'ActiveCache',
        function ($scope, $routeParams, $window, $location, $modal, Child,
                Address, RelationshipService, ActiveCache) {

            angular.extend($scope, {
                data: {
                    child: Child.get({id: $routeParams.childId}, function(response) {
                        ActiveCache.child = response;
                    }),
                    address: Address.getForPerson({personId: $routeParams.childId}),
                    relationships: RelationshipService.fetchRelationships($routeParams.childId)
                },
                viewData: {
                    submitLabel: 'Επεξεργασία',
                    hasChildrenIdsInScope: ActiveCache.childIds.length > 1
                }
            });
            
            $scope.cancel = function() {
                $location.url('/child/' + ActiveCache.child.id + '/view');
            };

            $scope.submit = function () {
                if ($scope.childForm.$pristine) {
                    console.log('Form is pristine');
                    $scope.toScopedChild();
                } else if ($scope.childForm.$invalid) {
                    console.log('Form is invalid');
                } else {
                    $scope.data.address.$save(function () {
                        $scope.data.child.$save({addressId: $scope.data.address.id, groupId: ActiveCache.group.id},
                            function() {
                                ActiveCache.child = $scope.data.child;
                                $location.url('/child/' + ActiveCache.child.id + '/view');
                            }
                        );
                    });
                }
            };

            $scope.addGuardian = function () {
                $location.path('/guardian/create');
            };

            $scope.goToGuardian = function ($event) {
                var clickedElement = angular.element($event.target);
                var guardianId = clickedElement.scope().relationship.guardian.id;
                $scope.go('/guardian/' + guardianId + '/view', $event);
            };

            $scope.confirmRemoveChild = function () {
                $modal.open({
                    templateUrl: 'application/child/remove-child.tpl.html',
                    controller: 'removeChildModalController',
                    resolve: {
                        child: function () {
                            return $scope.data.child;
                        }
                    }
                });
            };

            $scope.confirmRemoveRelationship = function (relationshipId, $event) {
                $event.stopPropagation();
                $modal.open({
                    templateUrl: 'application/child/remove-relationship.tpl.html',
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
        
        .controller('removeChildModalController', ['$location', '$rootScope', '$scope', '$modalInstance', 'child', 'ActiveCache',
            function ($location, $rootScope, $scope, $modalInstance, child, ActiveCache) {
                $scope.removeChild = function () {
                    child.$remove(function() {
                        ActiveCache.child = null;
                        $location.url('/group/' + ActiveCache.group.id);
                        $scope.dismiss();
                    });
                };
    
                $scope.dismiss = function () {
                    $modalInstance.dismiss();
                };
            }
        ])
    
        .controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'ActiveCache', 'Relationship', 'relationshipId',
            function ($scope, $modalInstance, ActiveCache, Relationship, relationshipId) {
                $scope.removeRelationship = function () {
                    Relationship.remove({id: relationshipId}).$promise.then(function (response) {
                        return Relationship.fetchRelationships({childId: ActiveCache.child.id}).$promise;
                    }).then(function (response) {
                        $scope.dismiss();
                        $scope.data.relationships = response;
                    });
                };
    
                $scope.dismiss = function () {
                    $modalInstance.dismiss();
                };
            }
        ]);
