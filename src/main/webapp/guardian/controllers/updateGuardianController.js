define(['angular', 'currentModule', 'templateCache!../edit.html', 'templateCache!../view.html', 'scripts/vendor/angular-uuid4/angular-uuid4'],
function(angular, currentModule) {
	'use strict';

    currentModule.addDependencies(['uuid4']).controller('updateGuardianController', ['$scope', '$routeParams', 'statefulChildService', 'guardianService', 'addressService', 'uuid4',
        function ($scope, $routeParams, statefulChildService, guardianService, addressService, uuid4) {
            angular.extend($scope, {
                data: {
                    guardian: null,
                    address: null,
                    relationship: null
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Ανανέωση"
                }

            });

            guardianService.fetch($routeParams.guardianId).then(function (response) {
                $scope.data.guardian = response;
                return addressService.fetch($scope.data.guardian.addressId).then(function (response) {
                    console.log('Setting address')
                    $scope.data.address = response;
                });
            });

            guardianService.fetchRelationship(statefulChildService.getScopedChildId(), $routeParams.guardianId).then(function (response) {
                    $scope.data.relationship = response;
                }
            );

            $scope.addTelephone = function () {
                var telephone = { id: uuid4.generate() };
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
