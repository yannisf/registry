'use strict';
angular.module('management').directive('breadcrumb', ['ActiveCache', 'School', 'Department', 'Group',
    function (ActiveCache, School, Department, Group) {

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
                scope.$watch('active', function(newval) {
					if (newval && newval.child && newval.child.id) {
						console.log('active child is: ', newval.child);
						scope.formattedName = formatName(newval.child);
					}
				}, true);
            }
        };
    }
]);
