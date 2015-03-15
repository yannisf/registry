'use strict';

angular.module('management')

    .directive('schools', ['uuid4', 'School', function (uuid4, School) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: "application/management/schools.tpl.html",
            controller: function($scope) {
                School.query().$promise.then(
                    function(response) {
                        $scope.data.schools = response;
                        $scope.viewData.schoolsLoading = false;
                    }
                );

                $scope.addSchool = function() {
                    var school = {
                        id: uuid4.generate(),
                        name: 'Νεο σχολείο',
                    };
                    $scope.data.schools = [];
                    $scope.setActiveSchool(null);
                    $scope.viewData.schoolsLoading = true;
                    School.save(school).$promise.then(
                        function(response) {
                            School.query().$promise.then(
                                function(response) {
                                    $scope.data.schools = response;
                                    $scope.viewData.schoolsLoading = false;
                                }
                            );
                        }
                    );
                };
            }
		};
	}]);