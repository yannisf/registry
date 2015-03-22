'use strict';

angular.module('guardian')

    .controller('updateGuardianController', ['$scope', '$routeParams', 'ChildService', 'Guardian', 'Relationship', 
            'AddressService', 'RelationshipService',
            
        function ($scope, $routeParams, ChildService, Guardian, Relationship, AddressService, RelationshipService) {
            angular.extend($scope, {
                data: {
                    guardian: Guardian.get({guardianId: $routeParams.guardianId}),
                    address: null,
                    relationship: Relationship.fetchRelationship({
                        childId: ChildService.child.id,
                        guardianId: $routeParams.guardianId
					})
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Ανανέωση"
                }
            });

            $scope.data.guardian.$promise.then(function (response) {
                $scope.data.address = AddressService.getForPerson($routeParams.guardianId);
            });

            $scope.submit = function () {
                RelationshipService.saveWithAddress($scope.data.address, $scope.data.guardian, $scope.data.relationship);
            };
        }
        
    ]);
