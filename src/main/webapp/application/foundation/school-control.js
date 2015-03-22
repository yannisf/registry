'use strict';

angular.module('management')

    .directive('schoolControl', ['School',
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
					$scope.updating = false;
					$scope.removing = false;
	
					$scope.remove = function() {
						$scope.removing = true;
						School.remove({id: $scope.school.id}).$promise.then(
							function() {
								var index = $scope.schools.indexOf($scope.school);
								$scope.schools.splice(index, 1);
								$scope.viewData.activeSchool = null;
								$scope.viewData.activeDepartment = null;
								$scope.viewData.activeGroup = null;
								$scope.removing = false;
							},
							function() {
								$scope.removing = false;
							}
						);
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
						$scope.updating = true;
						$scope.editMode = false;
						School.save($scope.school).$promise.then(
							function() {
								$scope.updating = false;
							}
						);
					};
				}
			};
	    }
    ]);