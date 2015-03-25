'use strict';

angular.module('management').directive('departmentControl', ['Department',
	function (Department) {
		return {
			restrict: 'A',
			scope: {
				department: "=departmentControl",
				departments: "=",
				viewData: "="
			},
			templateUrl: "application/foundation/department-control.tpl.html",
			controller: function($scope) {
				$scope.working = false;

				$scope.remove = function() {
					$scope.working = true;
					$scope.department.$remove({}, function() {
						var index = $scope.departments.indexOf($scope.department);
						$scope.departments.splice(index, 1);
						$scope.viewData.activeDepartment = null;
						$scope.viewData.activeGroup = null;
						$scope.viewData.activeSchool.numberOfDepartments--;
						$scope.working = false;
					});
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
					$scope.editMode = false;
					$scope.working = true;
					$scope.department.$save({}, function() {
						$scope.working = false;
					});
				};
			}
		};
	}
]);