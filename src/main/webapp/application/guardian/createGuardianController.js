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
        }
	]);