'use strict';

angular.module('guardian', ['ngRoute', 'ui.bootstrap', 'uuid4', 'child'])

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

    .service('guardianService', ['$http', function ($http) {
        return {
            fetch: function (guardianId) {
                return $http.get('api/guardian/' + guardianId).then(function (response) {
                        return response.data;
                    }
                );
            },
            fetchRelationship: function (childId, guardianId) {
                return $http.get('api/relationship/child/' + childId + '/guardian/' + guardianId).then(function (response) {
                        return response.data;
                    }
                );
            },
            updateGuardianAndRelationship: function (childId, guardianId, relationship) {
                return $http.put('api/relationship/child/' + childId + '/guardian/' + guardianId, relationship).then(function (response) {
                        return response.data;
                    }
                );
            }
        };
    }])

    .controller('createGuardianController', ['$scope', 'statefulChildService', 'guardianService', 'uuid4', 'addressService',
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
        }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'statefulChildService', 'guardianService', 'addressService', 'uuid4',
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
