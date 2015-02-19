'use strict';

angular.module('school', ['ngRoute', 'ngResource', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/school/list', {
                templateUrl: 'school/list.html',
                controller: 'listSchoolsController'
            })
    }])

    .factory('School', ['$resource', function($resource) {
        return $resource('api/school', { }, {
            query: {method: 'GET', isArray: true},
            info: {method: 'GET', url: 'api/school/info/:childGroupId'},
            statistics: {method: 'GET', url: 'api/school/group/:childGroupId/statistics'}
        });
    }])

    .service('SchoolService', ['School', function (School) {
        return {
            childGroupId: null,
            info: function() {
                if (angular.isDefined(this.childGroupId)) {
                    return School.info({childGroupId: this.childGroupId})
                }
            }
        }
    }])

    .controller('listSchoolsController', ['$scope', 'School', 'SchoolService',
        function ($scope, School, SchoolService) {
            angular.extend($scope, {
                data: {
                    schools: School.query()
                },
                viewData: {
                }
            });
        }
    ]);