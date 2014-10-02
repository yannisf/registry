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
            affinityTypes: function () {
                return $http.get('api/affinity/types').then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            create: function (guardian) {
                return $http.post('api/grownup', guardian).then(
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
                    affinityTypes: [],
                    submitLabel: 'Create'
                }
            });

            guardianService.affinityTypes().then(function (data) {
                $scope.viewData.affinityTypes = data;
                console.log('Affinities are: ', $scope.viewData.affinityTypes);
            });

            $scope.submit = function () {
                guardianService.create($scope.data.guardian).then(
                    function (response) {
                        $scope.toScopedChild();
                    }
                );
            };
        }]);
