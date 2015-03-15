'use strict';

angular.module('management')

    .directive('departments', ['uuid4', 'School', function (uuid4, School) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/departments.tpl.html",
            controller: function($scope) {
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
            }
		};
	}]);