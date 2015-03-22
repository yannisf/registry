'use strict';

angular.module('management')

    .directive('departments', ['uuid4', 'School', 'Department', 
        function (uuid4, School, Department) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: "application/foundation/departments.tpl.html",
                controller: function($scope) {
                
                    $scope.addDepartment = function() {
                        var department = {
                            id: uuid4.generate(),
                            name: 'Νεο τμήμα'
                        };
                        $scope.viewData.departmentsLoading = true;
                        $scope.setActiveDepartment(null);
                        Department.save({schoolId: $scope.viewData.activeSchool.id}, department).$promise.then(
                            function(response) {
                                School.departments({id: $scope.viewData.activeSchool.id}).$promise.then(
                                    function(response) {
                                        $scope.data.departments = response;
                                        $scope.viewData.departmentsLoading = false;
                                        $scope.viewData.activeSchool.numberOfDepartments++;
                                    }
                                );
                            }
                        );
                    };
                }
            };
    	}
	]);