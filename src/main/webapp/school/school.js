'use strict';

angular.module('school', ['ngRoute', 'ngResource', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/school/list', {
                templateUrl: 'school/list.html',
                controller: 'listSchoolsController'
            });
    }])

    .factory('School', ['$resource', function($resource) {
        return $resource('api/foundation', { }, {
            system: {method: 'GET', isArray: true},
            groupInfo: {method: 'GET', url: 'api/foundation/group/:groupId/info'},
            groupChildren: {method: 'GET', url: 'api/foundation/group/:groupId/children', isArray: true},
            groupStatistics: {method: 'GET', url: 'api/foundation/group/:groupId/statistics'}
        });
    }])

    .service('SchoolService', ['School', function (School) {
        return {
            groupId: null,
            info: function() {
                if (angular.isDefined(this.groupId)) {
                    return School.groupInfo({groupId: this.groupId});
                }
            }
        };
    }])

    .controller('listSchoolsController', ['$scope', 'School', function ($scope, School) {
            angular.extend($scope, {
                data: {
                    schools: School.system()
                },
                viewData: {
                }
            });
        }
    ]);