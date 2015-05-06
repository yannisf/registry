'use strict';
angular.module('overview').directive('breadcrumb', ['$location', '$cookieStore', 'ActiveCache', 'School', 'Department', 'Group',
    function ($location, $cookieStore, ActiveCache, School, Department, Group) {

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
            controller: ['$scope', function($scope) {
				$scope.active = ActiveCache;
                
                $scope.$watch('active', function(newval) {
					if (newval && newval.child && newval.child.id) {
						$scope.formattedName = formatName(newval.child);
					}
				}, true);
				
				$scope.toSchool = function() {
					ActiveCache.clearDepartment();
                    $cookieStore.remove('group');
					$location.url('/overview');
				};

				$scope.toDepartment = function() {
					ActiveCache.clearGroup();
					$cookieStore.remove('group');
					$location.url('/overview');
				};

				$scope.toGroup = function() {
					ActiveCache.clearChild();
					$location.url('/group/' + ActiveCache.group.id);
				};

				$scope.toChild = function() {
					$location.url('/child/' + ActiveCache.child.id + '/view');
				};

            }]
        };
    }
]);
