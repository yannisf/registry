'use strict';

angular.module('child', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4', 'relationship'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/group/:groupId', {
                templateUrl: 'application/child/list.html',
                controller: 'listGroupController'
            })
            .when('/child/create', {
                templateUrl: 'application/child/edit.html',
                controller: 'createChildController'
            })
            .when('/child/:childId/update', {
                templateUrl: 'application/child/edit.html',
                controller: 'updateChildController'
            })
            .when('/child/:childId/view', {
                templateUrl: 'application/child/view.html',
                controller: 'updateChildController'
            });
    }])

    .factory('Child', ['$resource', function($resource) {
        return $resource('api/child/:id', {id: '@id' }, {
            save: {method: 'PUT', params: {id: null} },
        });
    }

]);