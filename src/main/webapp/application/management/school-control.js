'use strict';

angular.module('management')

    .directive('schoolControl', ['School', function (School) {
        return {
            restrict: 'A',
            scope: {
                school: "=schoolControl",
                schools: "=",
                viewData: "="
            },
            templateUrl: "application/management/school-control.tpl.html",
            link: function(scope, element, attrs, controllers) {

            	scope.updating = false;
            	scope.removing = false;

                scope.remove = function() {
					scope.removing = true;
                	School.remove(scope.school).$promise.then(
						function() {
							var index = scope.schools.indexOf(scope.school);
							scope.schools.splice(index, 1);
							scope.viewData.activeSchool = null;
							scope.viewData.activeGroup = null;
							scope.removing = false;
						}
                	);
                };

				scope.update = function() {
					scope.updating = true;
					School.save(scope.school).$promise.then(
						function() {
							scope.updating = false;
						}
					);
				};
            }
        };
    }]);