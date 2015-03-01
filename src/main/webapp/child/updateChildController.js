'use strict';

angular.module('child')

    .controller('updateChildController', ['$scope', '$routeParams', '$window', '$location', '$modal', 'Child',
                'ChildService', 'Address', 'Relationship', 'FoundationService',
        function ($scope, $routeParams, $window, $location, $modal, Child,
                ChildService, Address, Relationship, FoundationService) {

            angular.extend($scope, {
                data: {
                    child: Child.get({id: $routeParams.childId}),
                    address: null,
                    relationships: null /*Relationship.fetchRelationships({childId: $routeParams.childId})*/
                },
                viewData: {
                    submitLabel: 'Ανανέωση',
                    hasChildrenIdsInScope: FoundationService.groupChildrenIds.length > 1
                }
            });

            $scope.data.child.$promise.then(function (response) {
                ChildService.setChild($scope.data.child);
                $scope.data.address = Address.getForChild({childId: $scope.data.child.id});
            });

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
                    console.log("Ανακύκλωση καταλόγου. ");
                }
                $location.url('/child/' + next.id + '/view');
            };

            $scope.previousChild = function () {
                var previous = findPreviousChild();
                if (previous.rollover) {
                    console.log("Ανακύκλωση καταλόγου. ");
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
                if (currentChildIdIndex !== 0) {
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
        }]);