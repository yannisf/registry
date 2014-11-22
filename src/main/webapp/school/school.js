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
            info: {method: 'GET', url: 'api/school/info/:yearClassId'}
        });
    }])

    .service('SchoolService', ['School', function (School) {
        return {
            yearClassId: null,
            info: function() {
                console.log("HIT: ", this.yearClassId)
                if (angular.isDefined(this.yearClassId)) {
                    return School.info({yearClassId: this.yearClassId})
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