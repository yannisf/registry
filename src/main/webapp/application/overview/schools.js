'use strict';

angular.module('overview').directive('schools', ['uuid4', 'School', 'ActiveCache',
    function (uuid4, School, ActiveCache) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/overview/schools.tpl.html",
            controller: function($scope) {
                $scope.data.schools = School.query({}, function() {
                	if (ActiveCache.school) {
                		console.log('Setting as active school: ', ActiveCache.school);
                		$scope.viewData.active.school = ActiveCache.school;
                	}
                });

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