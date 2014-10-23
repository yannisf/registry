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
            updateGuardianAndRelationship: function (childId, guardianId, guardianRelationship) {
                return $http.put('api/relationship/child/' + childId + '/guardian/' + guardianId, guardianRelationship).then(function (response) {
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
                    relationshipMetadata: {
                        type: null
                    }
                },
                viewData: {
                    submitLabel: "Εισαγωγή"
                }
            });

            $scope.addTelephone = function () {
                $scope.data.guardian.telephones.push({});
            };

            $scope.removeTelephone = function (telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
                $scope.data.guardian.addressId = $scope.data.address.id;
                var guardianRelationship = {
                    guardian: $scope.data.guardian,
                    relationship: $scope.data.relationship
                };

                addressService.update($scope.data.address).then(function (response) {
                    return guardianService.updateGuardianAndRelationship(statefulChildService.getScopedChildId(),
                        $scope.data.guardian.id, $scope.data.relationshipMetadata);
                }).then(function (response) {
                    $scope.toScopedChild();
                });
            };
        }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'statefulChildService',
        'guardianService', 'addressService',
        function ($scope, $routeParams, statefulChildService, guardianService, addressService) {
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
                    $scope.data.address = response;
                });
            });

            guardianService.fetchRelationship(statefulChildService.getScopedChildId(), $routeParams.guardianId).then(function (response) {
                    $scope.data.relationship = response;
                }
            );

            $scope.addTelephone = function () {
                $scope.data.guardian.telephones.push({});
            };

            $scope.removeTelephone = function (telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
                $scope.data.guardian.addressId = $scope.data.address.id;
                var guardianRelationship = {
                    guardian: $scope.data.guardian,
                    relationship: $scope.data.relationship
                };

                addressService.update($scope.data.address).then(function (response) {
                    return guardianService.updateGuardianAndRelationship(statefulChildService.getScopedChildId(), $scope.data.guardian.id, guardianRelationship);
                }).then(function (response) {
                    $scope.toScopedChild();
                });
            }
        }
    ]);
