'use strict';

angular.module('management', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4', 'foundation'])

    .config(['$routeProvider', 
        function ($routeProvider) {
            $routeProvider
                .when('/management', {
                    templateUrl: 'application/foundation/management.html',
                    controller: 'manageSchools'
                });
        }
    ])
    
    .factory('School', ['$resource', 
        function($resource) {
            return $resource('api/foundation/school/:id', { }, {
                save: {method: 'PUT'},
                remove: {method: 'DELETE'},
                departments: {method: 'GET', url: 'api/foundation/school/:id/department', isArray: true},
            });
        }
    ])

    .factory('Department', ['$resource',
        function($resource) {
            return $resource('api/foundation/department/:id', { }, {
                save: {method: 'PUT'},
                remove: {method: 'DELETE'},
                groups: {method: 'GET', url: 'api/foundation/department/:id/group', isArray: true},
            });
        }
    ])

    .factory('Group', ['$resource', 
        function($resource) {
            return $resource('api/foundation/group/:id', { }, {
                save: {method: 'PUT'},
                remove: {method: 'DELETE'},
                children: {method: 'GET', url: 'api/foundation/group/:id/child', isArray: true},
                info: {method: 'GET', url: 'api/foundation/group/:id/info'},
                statistics: {method: 'GET', url: 'api/foundation/group/:id/statistics'},
            });
        }
    ])

    .controller('manageSchools', ['$scope', 'uuid4', 'School', 'Department', 'Group', 
        function ($scope, uuid4, School, Department, Group) {
            angular.extend($scope, {
                data: {
                    schools: [],
                    departments: [],
                    groups: []
                },
                viewData: {
                    activeSchool: null,
                    activeDepartment: null,
                    activeGroup: null,
                    schoolsLoading: true,
                    departmentsLoading: false,
                    groupsLoading: false
                }
            });

            $scope.$watch('viewData.activeSchool', 
                function(newval) {
                    if (newval) {
                        $scope.data.departments = [];
                        $scope.viewData.activeDepartment = null;
                        $scope.viewData.departmentsLoading = true;
                        School.departments({id: newval.id}).$promise.then(
							function (response) {
								$scope.data.departments = response;
								$scope.viewData.departmentsLoading = false;
							}
                        );
                    }
                }
            );
            
            $scope.$watch('viewData.activeDepartment', 
                function(newval) {
                    if (newval) {
                        $scope.data.groups = [];
                        $scope.viewData.activeGroup = null;
                        $scope.viewData.groupsLoading = true;
                        Department.groups({
                            id: newval.id
                        }).$promise.then(
							function (response) {
								$scope.data.groups = response;
								$scope.viewData.groupsLoading = false;
							}
                        );
                    }
                }
            );
            
            $scope.setActiveSchool = function(school) {
                $scope.viewData.activeSchool = school;
                $scope.viewData.activeDepartment = null;
            };
            
            $scope.setActiveDepartment = function(department) {
                $scope.viewData.activeDepartment = department;
                $scope.viewData.activeGroup = null;
            };

            $scope.setActiveGroup = function(group) {
                $scope.viewData.activeGroup = group;
            };
        }
    ]);