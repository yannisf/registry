'use strict';

angular.module('schoolApp', ['ngRoute', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/child/create', {
                templateUrl: 'child/edit.html',
                controller: 'createChildController'
            })
            .when('/child/update', {
                templateUrl: 'child/edit.html',
                controller: 'updateChildController'
            })
            .when('/child/view', {
                templateUrl: 'child/view.html',
                controller: 'updateChildController'
            })
            .when('/child/list', {
                templateUrl: 'child/list.html',
                controller: 'ChildController'
            })
            .otherwise({
                redirectTo: '/child/list'
            });
    }])

    .run(['$rootScope', '$location', function($rootScope, $location) {
        angular.extend($rootScope, {
            cancel: function () {
                $location.url('/child/list');
            }
        });
    }])

    .service('childService', ['$http', function ($http) {
        return {
            fetch: function (childId) {
                return $http.get('api/child/' + childId).then(
                    function (response) {
                        return response.data;
                    });
            },
            fetchAll: function () {
                return $http.get('api/child/all').then(
                    function (response) {
                        return response.data;
                    });
            },
            create: function (child) {
                return $http.post('api/child', child).then(
                    function (response) {
                        return response.data;
                    });
            },
            update: function (childId, child) {
                return $http.post('api/child/' + childId, child).then(
                    function (response) {
                        return response.data;
                    });
            },
            remove: function (childId) {
                return $http({method: 'DELETE', url: 'api/child/' + childId}).then(
                    function (response) {
                        return response.data;
                    });
            }
        };
    }])

    .controller('ChildController', ['$scope', 'childService', function ($scope, childService) {
        childService.fetchAll().then(
            function (data) {
                $scope.children = data;
            }
        );
    }])

    .controller('createChildController', ['$scope', '$location', 'childService',
        function ($scope, $location, childService) {
            angular.extend($scope, {
                data: {
                    child: null
                },
                viewData: {
                    allowDelete: false,
                    submitLabel: 'Create'
                }
            });

            $scope.submit = function () {
                childService.create($scope.data.child).then(
                    function (response) {
                        $location.url('/child/list');
                    }
                );
            }
        }])

    .controller('updateChildController', ['$scope', '$routeParams', '$location', 'childService',
        function ($scope, $routeParams, $location, childService) {
            angular.extend($scope, {
                data: {
                    child: null
                },
                viewData: {
                    childId: $routeParams.id,
                    allowDelete: true,
                    submitLabel: 'Update'
                }
            });

            childService.fetch($scope.viewData.childId).then(
                function (response) {
                    $scope.data.child = response;
                }
            );

            $scope.submit = function () {
                childService.update($scope.viewData.childId, $scope.data.child).then(
                    function (response) {
                        console.log('DOB: ', $scope.data.child.dateOfBirth);
                        $location.url('/child/list');
                    }
                );
            };

            $scope.remove = function () {
                childService.remove($scope.viewData.childId).then(
                    function (response) {
                        $location.url('/child/list');
                    }
                );
            };


            ////////////////////
            $scope.today = function() {
                $scope.dt = new Date();
              };
              $scope.today();

              $scope.clear = function () {
                $scope.dt = null;
              };

              // Disable weekend selection
              $scope.disabled = function(date, mode) {
                return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
              };

              $scope.toggleMin = function() {
                $scope.minDate = $scope.minDate ? null : new Date();
              };
              $scope.toggleMin();

              $scope.open = function($event) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened = true;
              };

              $scope.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
              };
              /////////////////



        }]);
