'use strict';

angular.module('management')

    .directive('schoolControl', [function () {
        return {
            restrict: 'A',
            scope: {
                school: "=schoolControl",
                schools: "=",
                viewData: "="
            },
            require: ['^schoolControlCoordinator', 'schoolControl'],
            templateUrl: "management/school-control.tpl.html",
            link: function(scope, element, attrs, ctrls) {
            	var schoolControlCoordinator, schoolControl;

            	schoolControlCoordinator = ctrls[0];
            	schoolControl = ctrls[1];

                scope.remove = function() {
                    var index = scope.schools.indexOf(scope.school);
                    scope.schools.splice(index,1);
                    scope.viewData.activeSchool = null;
                    scope.viewData.activeGroup = null;
                };

                scope.activate = function() {
                	scope.editMode = true;
                	schoolControlCoordinator.setActiveControl(schoolControl);
                };

                scope.deactivate = function() {
                	scope.editMode = false;
					schoolControlCoordinator.setActiveControl(null);
                };

                scope.$on('$destroy', function() {
                	if( schoolControlCoordinator.getActiveControl === schoolControl ) {
                		scope.deactivate();
                	}
                });
            },
            controller: ['$scope', function($scope) {
				this.deactivate = function() {
					$scope.editMode = false;
				};
            }]
        };
    }])

    .directive('schoolControlCoordinator', function() {
    	return {
    		restrict: 'A',
    		scope: false,
    		controller: function() {
    			var activeControl;

    			this.setActiveControl = function(ac) {
    				if( activeControl != null ) {
    					activeControl.deactivate();
    				}
    				activeControl = ac;
    			};


    			this.getActiveControl = function() {
    				return activeControl;
    			};
    		}
    	};
    });
