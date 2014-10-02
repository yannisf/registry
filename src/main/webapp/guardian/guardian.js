'use strict';

angular.module('guardian', ['ngRoute', 'ui.bootstrap', 'child'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/guardian/edit', {
                templateUrl: 'guardian/edit.html',
                controller: 'guardianController'
            });
    }])

    .service('guardianService', ['$http', function ($http) {
        return {
            relationshipTypes: function () {
                return $http.get('api/relationship/types').then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            create: function (guardian) {
                return $http.post('api/guardian', guardian).then(
                    function (response) {
                        return response.data;
                    }
                );
            }
        };
    }])

    .controller('guardianController', ['$scope', 'statefulChildService', 'guardianService',
        function ($scope, statefulChildService, guardianService) {
            console.log('Add guardian controller. Scoped child: ', statefulChildService.getScopedChildId());
            angular.extend($scope, {
                data: {
                    guardian: null
                },
                viewData: {
                    relationshipTypes: [],
                    submitLabel: 'Create'
                }
            });

            guardianService.relationship().then(function (data) {
                $scope.viewData.relationshipTypes = data;
                console.log('Relationship are: ', $scope.viewData.relationshipTypes);
            });

            $scope.submit = function () {
                guardianService.create($scope.data.guardian).then(
                    function (response) {
                        $scope.toScopedChild();
                    }
                );
            };
        }]);
