'use strict';

angular.module('overview').directive('departments', ['uuid4', 'Department', 'ActiveCache',
    function (uuid4, Department, ActiveCache) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/overview/departments.tpl.html",
            controller: function($scope) {
				if (ActiveCache.department) {
					console.log('Setting as active department: ', ActiveCache.department);
					$scope.viewData.active.department = ActiveCache.department;
				}

                $scope.addDepartment = function() {
                    $scope.data.departments.$resolved = false;
                    var department = new Department({id: uuid4.generate(), name: 'Νεο τμήμα'});
                    $scope.setActiveDepartment(null);
                    department.$save({schoolId: $scope.viewData.active.school.id}, function() {
                        $scope.data.departments = Department.query({schoolId: $scope.viewData.active.school.id},
                            function() {
                                $scope.viewData.active.school.numberOfDepartments++;
                            }
                        );
                    });
                };
            }
        };
    }
]);
