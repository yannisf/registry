'use strict';

angular.module('guardian')

    .controller('updateGuardianController', ['$scope', '$routeParams', 'ActiveCache', 'Guardian', 'Relationship',
            'Address', 'RelationshipService',
            
        function ($scope, $routeParams, ActiveCache, Guardian, Relationship, Address, RelationshipService) {
            angular.extend($scope, {
                data: {
                    guardian: Guardian.get({guardianId: $routeParams.guardianId}),
                    address: null,
                    relationship: Relationship.fetchRelationship({
                        childId: ActiveCache.child.id,
                        guardianId: $routeParams.guardianId
					})
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Επεξεργασία"
                }
            });

            $scope.data.guardian.$promise.then(function (response) {
                $scope.data.address = Address.getForPerson({personId: $routeParams.guardianId});
            });

            $scope.submit = function () {
                RelationshipService.saveWithAddress($scope.data.address, $scope.data.guardian, $scope.data.relationship);
            };
        }
        
    ]);
