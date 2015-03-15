'use strict';

angular.module('management')

    .directive('schoolControl', ['$timeout', 'School', function ($timeout, School) {
        return {
            restrict: 'A',
            scope: {
                school: "=schoolControl",
                schools: "=",
                viewData: "="
            },
            templateUrl: "application/management/school-control.tpl.html",
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
							$timeout(function () {
                                alert('Το σχολείο δε μπόρεσε να διαγραφεί. Βεβαιωθείτε ότι δεν υπάρχουν υφιστάμενα τμήματα. ');
							});
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
    }]);