'use strict';

angular.module('foundation', ['ngRoute', 'ngResource', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/foundation/list', {
                templateUrl: 'foundation/list.html',
                controller: 'listSchoolsController'
            })
            .when('/group/:groupId', {
                templateUrl: 'foundation/group.html',
                controller: 'listChildController'
            });
    }])

    .factory('Foundation', ['$resource', function($resource) {
        return $resource('api/foundation', { }, {
            system: {method: 'GET', isArray: true},
            groupInfo: {method: 'GET', url: 'api/foundation/group/:groupId/info'},
            groupChildren: {method: 'GET', url: 'api/foundation/group/:groupId/children', isArray: true},
            groupStatistics: {method: 'GET', url: 'api/foundation/group/:groupId/statistics'}
        });
    }])

   .controller('listChildController', ['$scope', '$routeParams', 'Child', 'ChildService', 'Foundation',
       function ($scope, $routeParams, Child, ChildService, Foundation) {
           angular.extend($scope, {
               data: {
                   children: Foundation.groupChildren({groupId: $routeParams.groupId})
               },
               viewData: {
                   noChildren: true
               }
           });

           $scope.goToChild = function ($event) {
               var clickedElement = angular.element($event.target);
               var childId = clickedElement.scope().child.id;
               $scope.go('/child/' + childId + '/view', $event);
           };

           $scope.data.children.$promise.then(function(children) {
               ChildService.childIds = children.map(function (child) {
                   return child.id;
               });
               $scope.viewData.noChildren = children.length === 0;
           });

       }
   ])


    .controller('listSchoolsController', ['$scope', 'Foundation', function ($scope, Foundation) {
            angular.extend($scope, {
                data: {
                    schools: Foundation.system()
                },
                viewData: {
                }
            });
        }
    ]);

