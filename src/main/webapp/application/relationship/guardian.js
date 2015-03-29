'use strict';

angular.module('guardian', ['ngRoute', 'ui.bootstrap', 'uuid4', 'child', 'relationship'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/guardian/create', {
                templateUrl: 'application/relationship/edit.html',
                controller: 'createGuardianController'
            })
            .when('/guardian/:guardianId/edit', {
                templateUrl: 'application/relationship/edit.html',
                controller: 'updateGuardianController'
            })
            .when('/guardian/:guardianId/view', {
                templateUrl: 'application/relationship/view.html',
                controller: 'updateGuardianController'
            });
        }
    ])

    .factory('Guardian', ['$resource', function ($resource) {
        return $resource('api/guardian/:id', {id: '@id' }, {
        	save: {method: 'PUT', params: {id: null} },
        });
    }]);
