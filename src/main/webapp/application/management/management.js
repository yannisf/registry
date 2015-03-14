'use strict';

angular.module('management', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4', 'foundation'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/management', {
                templateUrl: 'application/management/management.html',
                controller: 'manageSchools'
            });
    }])
    
    .factory('School', ['$resource', function($resource) {
        return $resource('api/foundation/school/:id', { }, {
            save: {method: 'PUT'},
            remove: {method: 'DELETE'},
            fetchDepartmentsForSchool: {method: 'GET', url: 'api/foundation/school/:schoolId/department', isArray: true},
            fetchGroupsForDepartment: {method: 'GET', url: 'api/foundation/school/:schoolId/department/:departmentId/group', isArray: true},
            createOrUpdateDepartmentForSchool: {method: 'PUT', url: 'api/foundation/school/:schoolId/department'},
            removeDepartment: {method: 'DELETE', url: 'api/foundation/school/:schoolId/department/:departmentId'},
            createOrUpdateGroup: {method: 'GET', url: 'api/foundation/group'}
        });
    }])
    
    .factory('Department', ['$resource', 
        function($resource) {
            return $resource('api/foundation/group/:id', { }, {
                save: {method: 'PUT'}
            });
        }
    ])

    .factory('Group', ['$resource', 
        function($resource) {
            return $resource('api/foundation/group/:id', { }, {
                save: {method: 'PUT'}
            });
        }
    ])

    .controller('manageSchools', ['$scope', 'uuid4', 'School', 'Group', function ($scope, uuid4, School, Group) {
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

            School.query().$promise.then(
            	function(response) {
            		$scope.data.schools = response;
            		$scope.viewData.schoolsLoading = false;
            	}
            );

            $scope.$watch('viewData.activeSchool', 
                function(newval) {
                    if (newval) {
                        console.log('Active school is now ', newval);
                        $scope.data.departments = [];
                        $scope.viewData.activeDepartment = null;
                        $scope.viewData.departmentsLoading = true;
                        School.fetchDepartmentsForSchool({schoolId: newval.id}).$promise.then(
							function (response) {
								$scope.data.departments = response;
								$scope.viewData.activeSchool.departments = response;
								$scope.viewData.departmentsLoading = false;
							}
                        );
                    }
                }
            );
            
            $scope.$watch('viewData.activeDepartment', 
                function(newval) {
                    if (newval) {
                        console.log('Active department is now ', newval);
                        $scope.data.groups = [];
                        $scope.viewData.activeGroup = null;
                        $scope.viewData.groupsLoading = true;
                        School.fetchGroupsForDepartment({
                                schoolId: $scope.viewData.activeSchool.id, 
                                departmentId: newval.id}).$promise.then(
							function (response) {
							    console.log('Setting as groups ', response);
								$scope.data.groups = response;
								$scope.viewData.activeDepartment.groups = response;
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

            $scope.addSchool = function() {
                var school = {
                    id: uuid4.generate(),
                    name: 'Νεο σχολείο',
                };

				$scope.data.schools = [];
				$scope.setActiveSchool(null);
				$scope.viewData.schoolsLoading = true;
                School.save(school).$promise.then(
                    function(response) {
						School.query().$promise.then(
							function(response) {
								$scope.data.schools = response;
								$scope.viewData.schoolsLoading = false;
							}
						);
                    }
                );
            };

            $scope.addDepartment = function() {
                var department = {
                    id: uuid4.generate(),
                    name: 'Νεο τμήμα'
                };
                $scope.viewData.departmentsLoading = true;
                $scope.setActiveDepartment(null);
                School.createOrUpdateDepartmentForSchool({schoolId: $scope.viewData.activeSchool.id}, department).$promise.then(
                    function(response) {
                        School.fetchDepartmentsForSchool({schoolId: $scope.viewData.activeSchool.id}).$promise.then(
                            function(response) {
                                $scope.data.departments = response;
                                $scope.viewData.departmentsLoading = false;
                            }
                        );
                    }
                );
            };            
            
            $scope.addGroup = function() {
                var group = {
                    id: uuid4.generate(),
                    name: 'Νεα χρόνια'
                };
                $scope.viewData.groupsLoading = true;
                Group.save({departmentId: $scope.viewData.activeDepartment.id}, group).$promise.then(
                    function(response) {
                        School.fetchGroupsForDepartment({
                                schoolId: $scope.viewData.activeSchool.id, 
                                departmentId: $scope.viewData.activeDepartment.id}).$promise.then(
                            function(response) {
                                console.log('The groups are: ', response);
                                $scope.data.groups = response;
                                $scope.viewData.groupsLoading = false;
                            }
                        );
                    }
                );
            };
        }

    ]);