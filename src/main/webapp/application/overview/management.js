'use strict';

angular.module('management', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/management', {
                templateUrl: 'application/overview/management.html',
                controller: 'manageSchools'
            });
        }
    ])
    
    .factory('School', ['$resource', function($resource) {
        return $resource('api/overview/school/:id', {id: '@id'}, {
            save: {method: 'PUT', params: {id: null} },
        });
    }])

    .factory('Department', ['$resource', function($resource) {
        return $resource('api/overview/department/:id', {id: '@id'}, {
            query: {method: 'GET', url: 'api/overview/department', isArray: true},
            save: {method: 'PUT', params: {id: null} },
        });
    }])

    .factory('Group', ['$resource', function($resource) {
        return $resource('api/overview/group/:id', {id: '@id'}, {
            query: {method: 'GET', url: 'api/overview/group', isArray: true},
            save: {method: 'PUT', params: {id: null} },
            children: {method: 'GET', url: 'api/overview/group/:id/child', isArray: true},
            statistics: {method: 'GET', url: 'api/overview/group/:id/statistics'},
        });
    }])

    .service('ActiveCache', [function() {
    	var school;
    	var department;
    	var group;
    	var child;
    	var children;
    	var childIds;
    }])

    .controller('manageSchools', ['$scope', 'uuid4', 'School', 'Department', 'Group', 'ActiveCache',
        function ($scope, uuid4, School, Department, Group, ActiveCache) {
            angular.extend($scope, {
                data: {
                    schools: [],
                    departments: [],
                    groups: []
                },
                viewData: {
                    active: ActiveCache
                }
            });

            $scope.$watch('viewData.active.school', function(newval) {
                if (newval) {
                    $scope.data.departments = Department.query({schoolId: newval.id});
                }
            });
            
            $scope.$watch('viewData.active.department', function(newval) {
                if (newval) {
                    $scope.data.groups = Group.query({departmentId: newval.id});
                }
            });
            
            $scope.setActiveSchool = function(school) {
                $scope.viewData.active.school = school;
                $scope.setActiveDepartment(null);
            };
            
            $scope.setActiveDepartment = function(department) {
                $scope.viewData.active.department = department;
                $scope.setActiveGroup(null);
            };

            $scope.setActiveGroup = function(group) {
                $scope.viewData.active.group = group;
            };
        }
    ]);