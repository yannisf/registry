'use strict';

angular.module('management')

    .directive('groups', ['uuid4', 'Department', 'Group',
        function (uuid4,Department, Group) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: "application/foundation/groups.tpl.html",
                controller: function($scope) {
                    $scope.addGroup = function() {
                        var group = {
                            id: uuid4.generate(),
                            name: 'Νεα χρόνια'
                        };
                        $scope.viewData.groupsLoading = true;
                        Group.save({departmentId: $scope.viewData.activeDepartment.id}, group).$promise.then(
                            function(response) {
                                Department.groups({id: $scope.viewData.activeDepartment.id}).$promise.then(
                                    function(response) {
                                        $scope.data.groups = response;
                                        $scope.viewData.groupsLoading = false;
                                        $scope.viewData.activeDepartment.numberOfGroups++;
                                    }
                                );
                            }
                        );
                    };
                }
            };
    	}
	]);