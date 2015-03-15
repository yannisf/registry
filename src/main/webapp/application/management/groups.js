'use strict';

angular.module('management')

    .directive('groups', ['uuid4', 'School', 'Group', function (uuid4, School, Group) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/groups.tpl.html",
            controller: function($scope) {
                $scope.addGroup = function() {
                    var group = {
                        id: uuid4.generate(),
                        name: 'Νεα χρόνια'
                    };
                    $scope.viewData.groupsLoading = true;
                    Group.save({departmentId: $scope.viewData.activeDepartment.id}, group).$promise.then(
                        function(response) {
                            School.fetchGroupsForDepartment({
                                    schoolId: $scope.viewData.activeSchool.id,
                                    departmentId: $scope.viewData.activeDepartment.id}).$promise.then(
                                function(response) {
                                    console.log('The groups are: ', response);
                                    $scope.data.groups = response;
                                    $scope.viewData.groupsLoading = false;
                                }
                            );
                        }
                    );
                };
            }
		};
	}]);