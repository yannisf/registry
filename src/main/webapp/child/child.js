'use strict';

angular.module('child', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4', 'relationship'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/child/create', {
                templateUrl: 'child/edit.html',
                controller: 'createChildController'
            })
            .when('/child/:childId/update', {
                templateUrl: 'child/edit.html',
                controller: 'updateChildController'
            })
            .when('/child/:childId/view', {
                templateUrl: 'child/view.html',
                controller: 'updateChildController'
            });
    }])

    .factory('Child', ['$resource', function($resource) {
        return $resource('api/child/:id', { }, {
            save: {method: 'PUT', url: 'api/child'},
            saveWithAddress: {method: 'PUT', url: 'api/child/address'}
        });
    }])

    .service('ChildService', [function () {
        var child, childIds;
    }])


    .controller('removeChildModalController', ['$scope', '$modalInstance', 'Child', 'childId', 'ChildService',
        function ($scope, $modalInstance, Child, childId, ChildService) {
            $scope.removeChild = function () {
                Child.remove({id: childId}).$promise.then(function (response) {
                    ChildService.child = null;
                    $scope.dismiss();
                    $scope.toChildList();
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }])

    .controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'ChildService', 'Relationship', 'relationshipId',
        function ($scope, $modalInstance, ChildService, Relationship, relationshipId) {
            $scope.removeRelationship = function () {
                Relationship.remove({id: relationshipId}).$promise.then(function (response) {
                    return Relationship.fetchRelationships({childId: ChildService.child.id}).$promise;
                }).then(function (response) {
                    $scope.dismiss();
                    $scope.data.relationships = response;
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }]);
