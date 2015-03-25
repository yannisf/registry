'use strict';

angular.module('management').directive('schoolControl', ['School',
	function (School) {
		return {
			restrict: 'A',
			scope: {
				school: "=schoolControl",
				schools: "=",
				viewData: "="
			},
			templateUrl: "application/foundation/school-control.tpl.html",
			controller: function($scope) {
				$scope.working = false;

				$scope.remove = function() {
					$scope.working = true;
					$scope.school.$remove({}, function() {
						var index = $scope.schools.indexOf($scope.school);
						$scope.schools.splice(index, 1);
						$scope.viewData.activeSchool = null;
						$scope.viewData.activeDepartment = null;
						$scope.viewData.activeGroup = null;
						$scope.working = false;
					},
					function() {
						$scope.working = false;
					});
				};

				var oldValue = $scope.school.name;
				$scope.edit = function() {
					$scope.editMode = true;
				};

				$scope.cancel = function() {
					$scope.editMode = false;
					$scope.school.name = oldValue;
				};

				$scope.update = function() {
					$scope.editMode = false;
					$scope.working = true;
					$scope.school.$save({}, function() {
						$scope.working = false;
					});
				};
			}
		};
	}
]);