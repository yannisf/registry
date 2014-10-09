'use strict';

angular.module('guardian', ['ngRoute', 'child'])

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
                return $http.get('api/guardian/' + guardianId).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            fetchRelationship: function (childId, guardianId) {
                return $http.get('api/relationship/child/' + childId + '/guardian/' + guardianId).then( function (response) {
                        return response.data;
                    }
                );
            },
            createGuardianAndRelationship: function (childId, guardianRelationship) {
                return $http.post('api/relationship/child/' + childId + '/guardian', guardianRelationship).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            updateGuardianAndRelationship: function (childId, guardianId, guardianRelationship) {
                return $http.put('api/relationship/child/' + childId + '/guardian/' + guardianId, guardianRelationship).then( function (response) {
                        return response.data;
                    }
                );
            }
        };
    }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'statefulChildService',
        'guardianService', 'childService', 'addressService',
        function ($scope, $routeParams, statefulChildService, guardianService, childService, addressService) {
            angular.extend($scope, {
                data: {
                    guardian: null,
                    relationship: null
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Ανανέωση"
                }

            });

            guardianService.fetch($routeParams.guardianId).then( function (response) {
                    $scope.data.guardian = response;
                }
            );

            guardianService.fetchRelationship(statefulChildService.getScopedChildId(), $routeParams.guardianId).then(
                function (response) {
                    $scope.data.relationship = response;
                }
            );

            $scope.addTelephone = function() {
                $scope.data.guardian.telephones.push({});
            };

            $scope.removeTelephone = function(telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.useChildAddress = function() {
                console.log('Into controller')
                var childId = statefulChildService.getScopedChildId();
                childService.fetch(childId).then(function(response) {
                    var addressId = response.address.id;
                    return addressService.fetch(addressId);
                }).then(function(response) {
                    $scope.guardian.address = response;
                });
            };

            $scope.submit = function() {
                var guardianRelationship = {
                    guardian: $scope.data.guardian,
                    relationship: $scope.data.relationship
                };

                guardianService.updateGuardianAndRelationship(statefulChildService.getScopedChildId(),
                    $scope.data.guardian.id, guardianRelationship).then(function(respons){
                        $scope.toScopedChild();
                    })
            };

        }
    ])

    .controller('createGuardianController', ['$scope', 'statefulChildService', 'guardianService',
        function ($scope, statefulChildService, guardianService) {
            angular.extend($scope, {
                data: {
                    guardian: {
                        telephones: [{}]
                    },
                    relationship: {
                        relationshipMetadata: {
                            type: null
                        }
                    }
                },
                viewData: {
                    submitLabel: "Εισαγωγή"
                }
            });

            $scope.addTelephone = function() {
                $scope.data.guardian.telephones.push({});
            };

            $scope.removeTelephone = function(telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            };

            $scope.submit = function () {
                var guardianRelationship = {
                    guardian: $scope.data.guardian,
                    relationship: $scope.data.relationship
                };

                guardianService.createGuardianAndRelationship(statefulChildService.getScopedChildId(), guardianRelationship).then(
                    function (response) {
                        $scope.toScopedChild();
                    }
                );
            };
        }]);
