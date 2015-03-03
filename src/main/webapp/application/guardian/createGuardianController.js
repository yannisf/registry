'use strict';

angular.module('guardian')

    .controller('createGuardianController', ['$scope', 'uuid4', 'RelationshipService', 'ChildService',
        function ($scope, uuid4, RelationshipService, ChildService) {
            angular.extend($scope, {
                data: {
                    guardian: {
                        id: uuid4.generate(),
                        telephones: []
                    },
                    address: {
                        id: uuid4.generate()
                    },
                    relationship: {
                        id: uuid4.generate(),
                        metadata: {
                            type: null
                        }
                    }
                },
                viewData: {
                    submitLabel: "Εισαγωγή",
                    sharedAddress: false
                }
            });

            $scope.addTelephone = function () {
                var telephone = { id: uuid4.generate() };
                $scope.data.guardian.telephones.push(telephone);
            };

            $scope.removeTelephone = function (telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
            	console.log("[createGuardianController] Submitting form");
            	console.log("[createGuardianController] Child seems to be ", ChildService.child);
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
        }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'ChildService', 'Guardian', 'Relationship', 'Address', 'uuid4',
        function ($scope, $routeParams, ChildService, Guardian, Relationship, Address, uuid4) {
            angular.extend($scope, {
                data: {
                    guardian: Guardian.get({guardianId: $routeParams.guardianId}),
                    address: null,
                    relationship: Relationship.fetchRelationship({
                        childId: ChildService.child.id,
                        guardianId: $routeParams.guardianId})
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Ανανέωση"
                }

            });

            $scope.data.guardian.$promise.then(function (response) {
                $scope.data.address = Address.get({addressId: $scope.data.guardian.addressId});
            });

            $scope.addTelephone = function () {
                var telephone = { id: uuid4.generate() };
                $scope.data.guardian.telephones.push(telephone);
            };

            $scope.removeTelephone = function (telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
                var relationshipWithGuardianAndAddress = {
                    relationship: $scope.data.relationship,
                    guardian: $scope.data.guardian,
                    address: $scope.data.address
                };

                Relationship.saveWithGuardianAndAddress({
                    childId: ChildService.child.id,
                    guardianId: $scope.data.guardian.id
                }, relationshipWithGuardianAndAddress).$promise.then(
                    function (response) {
                        $scope.toScopedChild();
                    }
                );
            };
        }
    ]);
