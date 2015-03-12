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
        });
    }])

    .controller('manageSchools', ['$scope', 'uuid4', 'School', function ($scope, uuid4, School) {
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
                    activeSchoolHasDepartments: false,
                    activeDepartmentHasGroups: false,
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
                        $scope.viewData.departmentsLoading = true;
                        School.fetchDepartmentsForSchool({schoolId: newval.id}).$promise.then(
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
                        console.log('Active department is now ', newval);
                        $scope.viewData.groupsLoading = true;
                        School.fetchGroupsForDepartment({schoolId: $scope.viewData.activeSchool.id, departmentId: newval.id}).$promise.then(
							function (response) {
								$scope.data.groups = response;
								$scope.viewData.groupsLoading = false;
							}
                        );
                    }
                }
            );
            
            $scope.$watchCollection('data.departments',
                function(newval) {
                    $scope.viewData.activeSchoolHasDepartments = (newval && newval.length > 0);
                }
            );
            
            $scope.$watchCollection('data.groups',
                function(newval) {
                    $scope.viewData.activeDepartmentHasGroups = (newval && newval.length > 0);
                }
            );
            
            $scope.setActive = function(school) {
                $scope.viewData.activeSchool = school;
            };
            
            $scope.setActiveDepartment = function(department) {
                $scope.viewData.activeDepartment = department;
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
                School.createOrUpdateGroupForDepartment({departmentId: $scope.viewData.activeDepartment.id}, group).$promise.then(
                    function(response) {
                        School.fetchGroupsForDepartment({schoolId: $scope.viewData.activeSchool.id, departmentId: $scope.viewData.activeDepartment.id}).$promise.then(
                            function(response) {
                                $scope.data.groups = response;
                                $scope.viewData.groupsLoading = false;
                            }
                        );
                    }
                );
            };
            
        }

    ]);