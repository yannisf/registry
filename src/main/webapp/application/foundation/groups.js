'use strict';

angular.module('management').directive('groups', ['uuid4', 'Group',
    function (uuid4, Group) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/foundation/groups.tpl.html",
            controller: function($scope) {
                $scope.addGroup = function() {
                    $scope.data.groups.$resolved = false;
                    var group = new Group({id: uuid4.generate(), name: 'Νεα χρόνια'});
                    group.$save({departmentId: $scope.viewData.activeDepartment.id}, function() {
                        $scope.data.groups = Group.query({departmentId: $scope.viewData.activeDepartment.id}, function() {
                            $scope.viewData.activeDepartment.numberOfGroups++;
                        });
                    });
                };
            }
        };
    }
]);
