'use strict';

angular.module('management').directive('schoolControl', ['School', 'ActiveCache',
	function (School, ActiveCache) {
		return {
			restrict: 'A',
			scope: {
				school: "=schoolControl",
				schools: "=",
				viewData: "="
			},
			templateUrl: "application/foundation/school-control.tpl.html",
			link: function(scope, element) {
				element.bind('keypress', function(e) {
					console.log(e)
					if (e.keyCode === 13) {
						scope.update();
					} else if (e.keyCode === 27) {
						console.log('Canceling')
						scope.cancel();
					}
				});
			},
			controller: function($scope) {
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
					console.log('Invoked cancel');
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
			}
		};
	}
]);