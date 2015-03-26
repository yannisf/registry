'use strict';

angular.module('management').directive('departmentControl', ['Department', 'ActiveCache',
	function (Department, ActiveCache) {
		return {
			restrict: 'A',
			scope: {
				department: "=departmentControl",
				departments: "=",
				viewData: "="
			},
			templateUrl: "application/foundation/department-control.tpl.html",
			link: function(scope, element) {
				element.bind('keypress', function(e) {
					if (e.keyCode === 13) {
						scope.update();
					}
				});
			},
			controller: function($scope) {
				$scope.working = false;

				$scope.remove = function() {
					$scope.working = true;
					$scope.department.$remove({}, function() {
						var index = $scope.departments.indexOf($scope.department);
						$scope.departments.splice(index, 1);
						if ($scope.department.id === ActiveCache.department.id) {
							ActiveCache.department = null;
						}
						$scope.viewData.active.school.numberOfDepartments--;
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
					$scope.department.$save({schoolId: $scope.viewData.active.school.id}, function() {
						$scope.working = false;
					});
				};
			}
		};
	}
]);