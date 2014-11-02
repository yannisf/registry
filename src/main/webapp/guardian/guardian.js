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

    .factory('Guardian', ['$resource', function($resource) {
        return $resource('api/guardian/:guardianId', {guardianId: '@id'}, { });
    }])

    .service('guardianService', ['$http', function ($http) {
        return {
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

    .controller('createGuardianController', ['$scope', 'statefulChildService', 'guardianService', 'uuid4', 'Address',
        function ($scope, statefulChildService, guardianService, uuid4, Address) {
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
                $scope.data.guardian.addressId = $scope.data.address.id;
                $scope.data.relationship.guardian = $scope.data.guardian;

                Address.save($scope.data.address).$promise.then(function (response) {
                    return guardianService.updateGuardianAndRelationship(
                        statefulChildService.getScopedChildId(),
                        $scope.data.relationship.guardian.id,
                        $scope.data.relationship);
                }).then(function (response) {
                    $scope.toScopedChild();
                });
            };
        }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'statefulChildService', 'Guardian', 'guardianService', 'Address', 'uuid4',
        function ($scope, $routeParams, statefulChildService, Guardian, guardianService, Address, uuid4) {
            angular.extend($scope, {
                data: {
                    guardian: Guardian.get({guardianId: $routeParams.guardianId}),
                    address: null,
                    relationship: null
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Ανανέωση"
                }

            });

            $scope.data.guardian.$promise.then(function (response) {
                console.log("1")
                $scope.data.address = Address.get({addressId: $scope.data.guardian.addressId});
                console.log("2")
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

                Address.save($scope.data.address).$promise.then(function (response) {
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
