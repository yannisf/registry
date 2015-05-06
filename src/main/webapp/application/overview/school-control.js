'use strict';

angular.module('overview').directive('schoolControl', ['School', 'ActiveCache',
	function (School, ActiveCache) {
		return {
			restrict: 'A',
			scope: {
				school: "=schoolControl",
				schools: "=",
				viewData: "="
			},
			templateUrl: "application/overview/school-control.tpl.html",
			link: function(scope, element) {
				element.bind('keypress', function(e) {
					scope.$apply(function () {
						if (e.keyCode === 13) {
							scope.update();
						} else if (e.keyCode === 27) {
							scope.cancel();
						}
					});
				});
			},
			controller: ['$scope', function($scope) {
				$scope.working = false;

				$scope.remove = function() {
					$scope.working = true;
					$scope.school.$remove({}, function() {
						var index = $scope.schools.indexOf($scope.school);
						$scope.schools.splice(index, 1);
						if ($scope.school.id === ActiveCache.school.id) {
							ActiveCache.school = null;
						}
						$scope.working = false;
					},
					function() {
						$scope.working = false;
					});
				};

				var oldValue = $scope.school.name;
				$scope.edit = function() {
					$scope.editMode = true;
				};

				$scope.cancel = function() {
					$scope.editMode = false;
					$scope.school.name = oldValue;
				};

				$scope.update = function() {
					$scope.editMode = false;
					$scope.working = true;
					$scope.school.$save({}, function() {
						$scope.working = false;
					});
				};
			}]
		};
	}
]);