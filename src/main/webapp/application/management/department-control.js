'use strict';

angular.module('management')

    .directive('departmentControl', ['School', function (School) {
        return {
            restrict: 'A',
            scope: {
                department: "=departmentControl",
                departments: "=",
                viewData: "="
            },
            templateUrl: "application/management/department-control.tpl.html",
            controller: function($scope) {
            	$scope.updating = false;
            	$scope.removing = false;

                $scope.remove = function() {
					$scope.removing = true;
                	School.removeDepartment({
                		schoolId: $scope.viewData.activeSchool.id,
                		departmentId: $scope.department.id
					}).$promise.then(
						function() {
							var index = $scope.departments.indexOf($scope.department);
							$scope.departments.splice(index, 1);
							$scope.viewData.activeDepartment = null;
							$scope.viewData.activeGroup = null;
							$scope.removing = false;
							$scope.viewData.activeSchool.numberOfDepartments--;
						}
                	);
                };
                
				var oldValue = $scope.department.name;
				$scope.edit = function() {
					$scope.editMode = true;
				};
				
				$scope.cancel = function() {
					$scope.editMode = false;
					$scope.department.name = oldValue;
				};

				$scope.update = function() {
					$scope.updating = true;
					$scope.editMode = false;
					School.createOrUpdateDepartmentForSchool({
						schoolId: $scope.viewData.activeSchool.id
					}, $scope.department).$promise.then(
						function() {
							$scope.updating = false;
						}
					);
				};
            }
        };
    }]);