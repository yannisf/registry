'use strict';

angular.module('child')

    .controller('updateChildController', ['$scope', '$routeParams', '$window', '$location', '$modal', 'Child',
                'ChildService', 'Address', 'RelationshipService', 'FoundationService',
        function ($scope, $routeParams, $window, $location, $modal, Child,
                ChildService, Address, RelationshipService, FoundationService) {

            angular.extend($scope, {
                data: {
                    child: ChildService.fetch($routeParams.childId),
                    address: Address.getForPerson({personId: $routeParams.childId}),
                    relationships: RelationshipService.fetchRelationships($routeParams.childId)
                },
                viewData: {
                    submitLabel: 'Επεξεργασία',
                    hasChildrenIdsInScope: FoundationService.groupChildrenIds.length > 1
                }
            });

            console.log('[updateChildController] model child is ', $scope.data.child);

            $scope.submit = function () {
                if ($scope.childForm.$pristine) {
                    console.log('Form is pristine');
                    $scope.toScopedChild();
                } else if ($scope.childForm.$invalid) {
                    console.log('Form is invalid');
                } else {
                    ChildService.save($scope.data.child, $scope.data.address);
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
                        childId: function () {
                            return ChildService.child.id;
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
        }]);