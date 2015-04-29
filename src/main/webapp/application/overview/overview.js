'use strict';

angular.module('overview', ['ngRoute', 'ngResource', 'ngCookies', 'ui.bootstrap', 'uuid4'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/overview', {
                templateUrl: 'application/overview/overview.html',
                controller: 'overviewController'
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
    	this.school = null;
    	this.department = null;
    	this.group = null;
    	this.child = null;
    	this.children = null;
    	this.childIds = null;
    	
    	this.clearChild = function() {
    	    this.child = null;
    	};

    	this.clearGroup = function() {
    	    this.clearChild();
    	    this.children = null;
    	    this.childIds = null;
    	    this.group = null;
    	};
    	
    	this.clearDepartment = function() {
    	    this.clearGroup();
    	    this.department = null;
    	};
    	
    	this.clearSchool = function() {
    	    this.clearDepartment();
    	    this.school = null;
    	};
    	
    }])

    .controller('overviewController', ['$scope', 'uuid4', 'School', 'Department', 'Group', 'ActiveCache',
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
                ActiveCache.clearDepartment();
            };
            
            $scope.setActiveDepartment = function(department) {
                $scope.viewData.active.department = department;
                ActiveCache.clearGroup();
            };

            $scope.setActiveGroup = function(group) {
                $scope.viewData.active.group = group;
                ActiveCache.clearChild();
            };
        }
    ]);