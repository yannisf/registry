'use strict';

angular.module('guardian', ['ngRoute', 'child', 'commonSchoolServices'])

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
                return $http.get('api/child/' + childId + '/guardian/' + guardianId).then( function (response) {
                        return response.data;
                    }
                );
            },
            createGuardianAndRelationship: function (guardianRelationship) {
                return $http.post('api/guardian/relationship', guardianRelationship).then( function (response) {
                        return response.data;
                    }
                );
            },
            updateGuardianAndRelationship: function (guardianRelationship) {
                //TODO: Implement this properly
                return $http.post('api/guardian/relationship', guardianRelationship).then( function (response) {
                        return response.data;
                    }
                );
            }
        };
    }])

    .controller('updateGuardianController', ['$scope', '$routeParams', 'statefulChildService', 'guardianService', 'ListService',
        function ($scope, $routeParams, statefulChildService, guardianService, ListService) {
            angular.extend($scope, {
                data: {
                    guardian: null,
                    relationship: null
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    relationshipTypes: [],
                    telephoneTypes: [],
                    submitLabel: "Ανανέωση"
                }

            });

            ListService.relationshipTypes().then(function (data) {
                $scope.viewData.relationshipTypes = data;
            });

            ListService.telephoneTypes().then(function (data) {
                $scope.viewData.telephoneTypes = data;
            });

            $scope.addTelephone = function() {
                $scope.data.guardian.telephones.push({});
            }

            $scope.removeTelephone = function(telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            }

            guardianService.fetch($routeParams.guardianId).then( function (response) {
                    $scope.data.guardian = response;
                }
            );

            guardianService.fetchRelationship(statefulChildService.getScopedChildId(), $routeParams.guardianId).then(
                function (response) {
                    $scope.data.relationship = response;
                }
            );

        }
    ])

    .controller('createGuardianController', ['$scope', 'statefulChildService', 'guardianService', 'ListService',
        function ($scope, statefulChildService, guardianService, ListService) {
            angular.extend($scope, {
                data: {
                    guardian: {
                        telephones: []
                    },
                    relationship: {
                        relationshipMetadata: {
                            type: null
                        }
                    }
                },
                viewData: {
                    relationshipTypes: [],
                    telephoneTypes: [],
                    submitLabel: "Εισαγωγή"
                }
            });

            ListService.relationshipTypes().then(function (data) {
                $scope.viewData.relationshipTypes = data;
            });

            ListService.telephoneTypes().then(function (data) {
                $scope.viewData.telephoneTypes = data;
            });

            $scope.addTelephone = function() {
                $scope.data.guardian.telephones.push({});
            }

            $scope.removeTelephone = function(telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex, 1);
            }

            $scope.submit = function () {
                var guardianRelationship = {
                    child: {
                        id: statefulChildService.getScopedChildId()
                    },
                    guardian: $scope.data.guardian,
                    relationship: $scope.data.relationship
                }

                guardianService.createGuardianAndRelationship(guardianRelationship).then(function (response) {
                        $scope.toScopedChild();
                    }
                );
            };
        }]);
