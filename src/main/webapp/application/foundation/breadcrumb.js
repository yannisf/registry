'use strict';
angular.module('foundation')

    .directive('breadcrumb', ['FoundationService', 'ChildService', function (FoundationService, ChildService) {

		function formatName (child) {
			var name = child.firstName ? child.firstName + " " : "";
			if (child.callName) {
				name += "(" + child.callName + ") ";
			}
			name = (name + " " + (child.lastName ? child.lastName + " " : "")).trim();
			if (name.length === 0) {
				name = null;
			}
			return name;
		}

        return {
            restrict: 'E',
            replace: true,
            scope: true,
            templateUrl: "application/foundation/breadcrumb.tpl.html",
            link: function(scope) {
                scope.school = FoundationService.school;
                scope.department = FoundationService.department;
                scope.group = FoundationService.group;
                scope.$watch(
                	function() {
                		return {
                			firstName: ChildService.child.firstName,
                			callName: ChildService.child.callName,
                			lastName: ChildService.child.lastName
                		};
                	},
                	function(newval) {
                		scope.formattedName = formatName(newval);
                	},
                	true
                );
            }
        };
    }]);
