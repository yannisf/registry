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
            fetch: {method: 'GET', isArray: true}
        });
    }])

    .service('SchoolService', [function () {
        var school,clazz,year,yearClassId;
    }])


    .controller('listSchoolsController', ['$scope', 'School', 'SchoolService',
        function ($scope, School, SchoolService) {
            angular.extend($scope, {
                data: {
                    schools: School.fetch()
                },
                viewData: { }
            });

            function walk(nodes) {
                console.log("Nodes: ", nodes);
                for(var node in nodes) {
                    console.log(node);
                    if (node.length > 1) {
                        walk(node);
                    }
                }
            }

            var unwatch = $scope.$watchCollection('data.schools', function(newval) {
                if (newval.$resolved) {
                    console.log('Schools are: ', newval);
                    walk(newval);
                    SchoolService.schools = newval; //How to cache only the array and not the resource?
                    unwatch();
                }
            });
        }
    ]);