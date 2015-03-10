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
            fetchClassroomsForSchool: {method: 'GET', url: 'api/foundation/school/:schoolId/classroom', isArray: true},
        });
    }])

    .controller('manageSchools', ['$scope', 'uuid4', 'School', function ($scope, uuid4, School) {
            angular.extend($scope, {
                data: {
                    schools: [],
                    groups: []
                },
                viewData: {
                    activeSchool: null,
                    activeGroup: null,
                    activeSchoolHasGroups: false,
                    schoolsLoading: true,
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
                        $scope.viewData.groupsLoading = true;
                        School.fetchClassroomsForSchool({schoolId: newval.id}).$promise.then(
							function (response) {
								$scope.data.groups = response;
								$scope.viewData.groupsLoading = false;
							}
                        );
                    }
                }
            );
            
            $scope.$watchCollection('data.groups',
                function(newval) {
                    $scope.viewData.activeSchoolHasGroups = (newval && newval.length > 0);
                }
            );
            
            $scope.setActive = function(school, event) {
                $scope.viewData.activeSchool = school;
            };
            
            $scope.setActiveGroup = function(group, event) {
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

            $scope.addGroup = function() {
                var group = {
                    id: uuid4.generate(),
                    name: 'Νεο τμήμα'
                };
                $scope.viewData.activeSchool.groups.push(group);
            };
        }

    ]);