'use strict';

angular.module('guardian', ['ngRoute', 'ui.bootstrap', 'uuid4', 'child', 'relationship'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/guardian/create', {
                templateUrl: 'application/guardian/edit.html',
                controller: 'createGuardianController'
            })
            .when('/guardian/:guardianId/edit', {
                templateUrl: 'application/guardian/edit.html',
                controller: 'updateGuardianController'
            })
            .when('/guardian/:guardianId/view', {
                templateUrl: 'application/guardian/view.html',
                controller: 'updateGuardianController'
            });
        }
    ])

    .factory('Guardian', ['$resource', function ($resource) {
        return $resource('api/guardian/:guardianId', { }, {
        	save: {method: 'PUT', url: 'api/guardian'},
        });
    }]);
