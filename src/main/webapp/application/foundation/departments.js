'use strict';

angular.module('management').directive('departments', ['uuid4', 'Department',
    function (uuid4, Department) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/foundation/departments.tpl.html",
            controller: function($scope) {
                $scope.addDepartment = function() {
                    $scope.data.departments.$resolved = false;
                    var department = new Department({id: uuid4.generate(), name: 'Νεο τμήμα'});
                    $scope.setActiveDepartment(null);
                    department.$save({schoolId: $scope.viewData.activeSchool.id}, function() {
                        $scope.data.departments = Department.query({schoolId: $scope.viewData.activeSchool.id}, 
                            function() {
                                $scope.viewData.activeSchool.numberOfDepartments++;
                            }
                        );
                    });
                };
            }
        };
    }
]);
