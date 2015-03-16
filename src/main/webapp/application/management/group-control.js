'use strict';

angular.module('management')

    .directive('groupControl', ['$rootScope', 'Group', function ($rootScope, Group) {
        return {
            restrict: 'A',
            scope: {
                group: "=groupControl",
                groups: "=",
                viewData: "="
            },
            templateUrl: "application/management/group-control.tpl.html",
            controller: function($scope) {
            	$scope.updating = false;
            	$scope.removing = false;
            	
            	$scope.toChildList = $rootScope.toChildList;

                $scope.remove = function() {
					$scope.removing = true;
                	Group.remove({ id: $scope.group.id }).$promise.then(
						function() {
							var index = $scope.groups.indexOf($scope.group);
							$scope.groups.splice(index, 1);
							$scope.viewData.activeGroup = null;
							$scope.removing = false;
							$scope.viewData.activeDepartment.numberOfGroups--;
						}
                	);
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
					$scope.updating = true;
					$scope.editMode = false;
					Group.save({departmentId: $scope.viewData.activeDepartment.id}, $scope.group).$promise.then(
						function() {
							$scope.updating = false;
						}
					);
				};
            }
        };
    }]);
    