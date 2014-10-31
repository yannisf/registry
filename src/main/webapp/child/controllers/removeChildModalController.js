define(['angular', 'currentModule', 'scripts/vendor/angular-bootstrap/ui-bootstrap-tpls'],
function(angular, currentModule) {
	'use strict';

    currentModule.addDependencies(['ui.bootstrap']).controller('removeChildModalController', ['$scope', '$modalInstance', 'childService', 'childId',
        function ($scope, $modalInstance, childService, childId) {
            $scope.removeChild = function () {
                childService.remove(childId).then(function (response) {
                    $modalInstance.dismiss();
                    $scope.toChildList();
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }
    ]);
});
