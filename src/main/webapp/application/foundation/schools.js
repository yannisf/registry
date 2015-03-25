'use strict';

angular.module('management').directive('schools', ['uuid4', 'School',
    function (uuid4, School) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/foundation/schools.tpl.html",
            controller: function($scope) {
                $scope.data.schools = School.query();

                $scope.addSchool = function() {
                    $scope.data.schools.$resolved = false;
                    var school = new School({id: uuid4.generate(), name: 'Νεο σχολείο'});
                    $scope.setActiveSchool(null);
                    school.$save({}, function() {
                        $scope.data.schools = School.query();
                    });
                };
            }
        };
    }
]);