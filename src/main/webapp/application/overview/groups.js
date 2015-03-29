'use strict';

angular.module('overview').directive('groups', ['uuid4', 'Group', 'ActiveCache',
    function (uuid4, Group, ActiveCache) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/overview/groups.tpl.html",
            controller: function($scope) {
				if (ActiveCache.group) {
					$scope.viewData.active.group = ActiveCache.group;
				}

                $scope.addGroup = function() {
                    $scope.data.groups.$resolved = false;
                    var group = new Group({id: uuid4.generate(), name: 'Νεα χρόνια'});
                    group.$save({departmentId: $scope.viewData.active.department.id}, function() {
                        $scope.data.groups = Group.query({departmentId: $scope.viewData.active.department.id}, function() {
                            $scope.viewData.active.department.numberOfGroups++;
                        });
                    });
                };
            }
        };
    }
]);
