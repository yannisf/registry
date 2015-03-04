'use strict';

angular.module('guardian')

    .controller('updateGuardianController', ['$scope', '$routeParams', 'ChildService', 'Guardian', 'Relationship', 'Address', 'uuid4', 'RelationshipService',
        function ($scope, $routeParams, ChildService, Guardian, Relationship, Address, uuid4, RelationshipService) {
            angular.extend($scope, {
                data: {
                    guardian: Guardian.get({guardianId: $routeParams.guardianId}),
                    address: null,
                    relationship: Relationship.fetchRelationship({
                        childId: ChildService.child.id,
                        guardianId: $routeParams.guardianId
					})
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Ανανέωση"
                }
            });

            $scope.data.guardian.$promise.then(function (response) {
                $scope.data.address = Address.getForPerson({personId: $routeParams.guardianId});
            });

            $scope.addTelephone = function () {
                var telephone = { id: uuid4.generate() };
                $scope.data.guardian.telephones.push(telephone);
            };

            $scope.removeTelephone = function (telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
            	console.log("[updateGuardianController] Submitting form");
            	console.log("[updateGuardianController] Child seems to be ", ChildService.child);
				if ($scope.viewData.sharedAddress) {
				    console.log('callback will be ', $scope.toScopedChild);
            		RelationshipService.saveWithAddress(
            		        $scope.data.address.id,
            		        $scope.data.guardian,
            		        $scope.data.relationship,
            		        $scope.toScopedChild);
                } else {
                    console.log('callback will be ', $scope.toScopedChild);
                	RelationshipService.saveWithoutAddress(
                	        $scope.data.address,
                	        $scope.data.guardian,
                	        $scope.data.relationship,
                	        $scope.toScopedChild);
                }
            };
        }
    ]);
