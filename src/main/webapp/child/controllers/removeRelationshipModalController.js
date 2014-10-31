define(['angular', 'currentModule', 'scripts/vendor/angular-bootstrap/ui-bootstrap-tpls'],
function(angular, currentModule) {
	'use strict';

    currentModule.addDependencies(['ui.bootstrap']).controller('removeRelationshipModalController', ['$scope', '$modalInstance', 'childService', 'statefulChildService', 'relationshipId',
        function ($scope, $modalInstance, childService, statefulChildService, relationshipId) {
            $scope.removeRelationship = function () {
                childService.removeRelationship(relationshipId).then(function (response) {
                    return childService.fetchRelationships(statefulChildService.getScopedChildId());
                }).then(function (response) {
                    $modalInstance.dismiss();
                    $scope.data.relationships = response;
                });
            };

            $scope.dismiss = function () {
                $modalInstance.dismiss();
            };
        }
    ]);
});
