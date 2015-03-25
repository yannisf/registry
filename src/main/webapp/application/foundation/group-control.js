'use strict';

angular.module('management').directive('groupControl', ['$rootScope', 'Group', function ($rootScope, Group) {
	return {
		restrict: 'A',
		scope: {
			group: "=groupControl",
			groups: "=",
			viewData: "="
		},
		templateUrl: "application/foundation/group-control.tpl.html",
		controller: function($scope) {
			$scope.working = false;

			$scope.toChildList = $rootScope.toChildList;

			$scope.remove = function() {
				$scope.working = true;
				$scope.group.$remove({}, function() {
					var index = $scope.groups.indexOf($scope.group);
					$scope.groups.splice(index, 1);
					$scope.viewData.activeGroup = null;
					$scope.viewData.activeDepartment.numberOfGroups--;
					$scope.working = false;
				});
			};

			var oldValue = $scope.group.name;
			$scope.edit = function() {
				$scope.editMode = true;
			};

			$scope.cancel = function() {
				$scope.editMode = false;
				$scope.group.name = oldValue;
			};

			$scope.update = function() {
				$scope.working = true;
				$scope.editMode = false;
				$scope.group.$save({departmentId: $scope.viewData.activeDepartment.id}, function() {
					$scope.working = false;
				});
			};
		}
	};
}]);
