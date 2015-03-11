'use strict';

angular.module('management')

    .directive('departmentControl', [function () {
        return {
            restrict: 'A',
            scope: {
                department: "=departmentControl",
                departments: "="
            },
            templateUrl: "application/management/department-control.tpl.html",
            link: function(scope) {
            	scope.updating = false;
            	scope.removing = false;

                scope.remove = function() {
					scope.removing = true;
                	School.removeDepartment(scope.department.id).$promise.then(
						function() {
							var index = scope.departments.indexOf(scope.department);
							scope.departments.splice(index, 1);
							scope.viewData.activeDepartment = null;
							scope.viewData.activeGroup = null;
							scope.removing = false;
						}
                	);
                };

				scope.update = function() {
					scope.updating = true;
					School.saveDepartment(scope.department.id).$promise.then(
						function() {
							scope.updating = false;
						}
					);
				};
            }
        };
    }]);