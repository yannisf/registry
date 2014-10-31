define(['angular', 'currentModule', 'templateCache!../list.html'],
function(angular, currentModule) {
	'use strict';

    currentModule.controller('listChildController', ['$scope', 'childService', 'statefulChildService',
        function ($scope, childService, statefulChildService) {
            angular.extend($scope, {
                data: {
                    children: []
                },
                viewData: {
                    noChildren: true
                    }
            });

			$scope.goToChild = function ($event) {
				var clickedElement = angular.element($event.target);
				var childId = clickedElement.scope().child.id;
				$scope.go('/child/' + childId + '/view', $event);
			}

			childService.fetchAll().then(function (data) {
				$scope.data.children = data;
			});

            $scope.$watch('data.children', function(newval) {
                var childIds = newval.map(function(child) {
                    return child.id.toString();
                });
                statefulChildService.setChildIds(childIds);
                $scope.viewData.noChildren = newval.length == 0;
            })

        }
    ]);
});
