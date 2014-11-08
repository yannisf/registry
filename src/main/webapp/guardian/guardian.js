'use strict';

angular.module('guardian', ['ngRoute', 'ui.bootstrap', 'uuid4', 'child', 'relationship'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/guardian/edit', {
                templateUrl: 'guardian/edit.html',
                controller: 'createGuardianController'
            })
            .when('/guardian/:guardianId/edit', {
                templateUrl: 'guardian/edit.html',
                controller: 'updateGuardianController'
            })
            .when('/guardian/:guardianId/view', {
                templateUrl: 'guardian/view.html',
                controller: 'updateGuardianController'
            });
    }])

    .factory('Guardian', ['$resource', function ($resource) {
        return $resource('api/guardian/:guardianId', { }, { });
    }])

    .controller('createGuardianController', ['$scope', 'Flash', 'ChildService', 'Relationship', 'uuid4', 'Address',
        function ($scope, Flash, ChildService, Relationship, uuid4, Address) {
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
                }

                Relationship.saveWithGuardianAndAddress({
                    childId: ChildService.child.id,
                    guardianId: $scope.data.guardian.id
                }, relationshipWithGuardianAndAddress)
                    .$promise.then(function (response) {
                        Flash.setSuccessMessage("Επιτυχής καταχώρηση.");
                        $scope.toScopedChild();
                    }, function (response) {
                        Flash.setWarningMessage("Σφάλμα καταχώρησης.");
                });
            }
        }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'Flash', 'ChildService', 'Guardian', 'Relationship', 'Address', 'uuid4',
        function ($scope, $routeParams, Flash, ChildService, Guardian, Relationship, Address, uuid4) {
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
                }

                Relationship.saveWithGuardianAndAddress({
                    childId: ChildService.child.id,
                    guardianId: $scope.data.guardian.id
                }, relationshipWithGuardianAndAddress).$promise.then(
                    function (response) {
                        Flash.setSuccessMessage("Επιτυχής καταχώρηση.");
                        $scope.toScopedChild();
                    },
                    function (response) {
                        Flash.setWarningMessage("Σφάλμα καταχώρησης.");
                    }
                );
            }
        }
    ]);
