'use strict';
angular.module('foundation').directive('breadcrumb', ['FoundationService', 'ChildService', 'ActiveCache',
    function (FoundationService, ChildService, ActiveCache) {

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
            templateUrl: "application/components/breadcrumb.tpl.html",
            link: function(scope) {
				scope.active = ActiveCache;
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
    }
]);
