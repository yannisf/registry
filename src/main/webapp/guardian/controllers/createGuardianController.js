define(['angular', 'currentModule', 'templateCache!../edit.html', 'scripts/vendor/angular-uuid4/angular-uuid4'],
function(angular, currentModule) {
	'use strict';

    currentModule.addDependencies(['uuid4']).controller('createGuardianController', ['$scope', 'statefulChildService', 'guardianService', 'uuid4', 'addressService',
        function ($scope, statefulChildService, guardianService, uuid4, addressService) {
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
                    submitLabel: "Εισαγωγή"
                }
            });

            $scope.addTelephone = function () {
                var telephone = { id: uuid4.generate() }
                $scope.data.guardian.telephones.push(telephone);
            };

            $scope.removeTelephone = function (telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
                $scope.data.guardian.addressId = $scope.data.address.id;
                $scope.data.relationship.guardian = $scope.data.guardian;

                addressService.update($scope.data.address).then(function (response) {
                    return guardianService.updateGuardianAndRelationship(
                        statefulChildService.getScopedChildId(),
                        $scope.data.relationship.guardian.id,
                        $scope.data.relationship);
                }).then(function (response) {
                    $scope.toScopedChild();
                });
            };
        }
    ]);
});
