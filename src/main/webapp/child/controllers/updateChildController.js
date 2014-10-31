define(['angular', 'currentModule', 'templateCache!../edit.html', 'templateCache!../view.html', 'scripts/vendor/angular-bootstrap/ui-bootstrap-tpls'],
function(angular, currentModule) {
	'use strict';

    currentModule.addDependencies(['ui.bootstrap']).controller('updateChildController', ['$scope', '$location', '$modal', 'childService', 'statefulChildService', 'addressService',
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
            	console.log(relationshipId)
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
        }
    ]);
});
