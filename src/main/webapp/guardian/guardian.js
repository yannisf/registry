'use strict';

angular.module('guardian', ['ngRoute', 'ui.bootstrap', 'child'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/guardian/edit', {
                templateUrl: 'guardian/edit.html',
                controller: 'createGuardianController'
            })
            .when('/guardian/:guardianId/edit', {
                templateUrl: 'guardian/edit.html',
                controller: 'guardianController'
            })
            .when('/guardian/:guardianId/view', {
                templateUrl: 'guardian/view.html',
                controller: 'guardianController'
            });
    }])

    .service('ListService', ['$http', function ($http) {
        return {
            relationshipTypes: function () {
                return $http.get('api/relationship/types').then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            telephoneTypes: function () {
                return $http.get('api/guardian/telephone/types').then(
                    function (response) {
                        return response.data;
                    });
                }
            };
        }
    ])

    .service('guardianService', ['$http', function ($http) {
        return {
            fetch: function (guardianId) {
                return $http.get('api/guardian/' + guardianId).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            createAndAssociate: function (guardianRelationship) {
                return $http.post('api/guardian/relationship', guardianRelationship).then(
                    function (response) {
                        return response.data;
                    }
                );
            }
        };
    }])

    .controller('guardianController', ['$scope', '$routeParams', 'statefulChildService', 'guardianService', 'ListService',
        function ($scope, $routeParams, statefulChildService, guardianService, ListService) {
            angular.extend($scope, {
                data: {
                    guardian: null,
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: 'Ανανέωση'
                }

            });

            guardianService.fetch($routeParams.guardianId).then(
                function (response) {
                    $scope.data.guardian = response;
                }
            );
        }
    ])

    .controller('createGuardianController', ['$scope', 'statefulChildService', 'guardianService', 'ListService',
        function ($scope, statefulChildService, guardianService, ListService) {
            console.log('Add guardian controller. Scoped child: ', statefulChildService.getScopedChildId());
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
                    submitLabel: 'Εισαγωγή'
                }
            });

            ListService.relationshipTypes().then(function (data) {
                $scope.viewData.relationshipTypes = data;
            });

            ListService.telephoneTypes().then(function (data) {
                $scope.viewData.telephoneTypes = data;
            });

            $scope.addTelephone = function() {
                $scope.data.guardian.telephones.push({number:null, type: null});
            }

            $scope.removeTelephone = function(telephoneIndex) {
                $scope.data.guardian.telephones.splice(telephoneIndex,1);
            }

            $scope.submit = function () {
                var guardianRelationship = {
                    child: {
                        id: statefulChildService.getScopedChildId()
                    },
                    guardian: $scope.data.guardian,
                    relationship: $scope.data.relationship
                }

                guardianService.createAndAssociate(guardianRelationship).then(
                    function (response) {
                        $scope.toScopedChild();
                    }
                );
            };
        }]);
