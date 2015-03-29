'use strict';

angular.module('guardian')

    .controller('updateGuardianController', ['$location', '$scope', '$routeParams', 'ActiveCache', 'Guardian', 
            'Relationship', 'Address',
            
        function ($location, $scope, $routeParams, ActiveCache, Guardian, Relationship, Address) {
            angular.extend($scope, {
                data: {
                    guardian: Guardian.get({id: $routeParams.guardianId}),
                    address: Address.getForPerson({personId: $routeParams.guardianId}),
                    relationship: Relationship.get({
                        childId: ActiveCache.child.id,
                        guardianId: $routeParams.guardianId
					})
                },
                viewData: {
                    guardianId: $routeParams.guardianId,
                    submitLabel: "Επεξεργασία"
                }
            });
            
            $scope.cancel = function () {
                $scope.go('/child/' + ActiveCache.child.id + '/view');
            };

            $scope.submit = function () {
                $scope.data.address.$save().then(function() {
                    return $scope.data.guardian.$save({
                        addressId: $scope.data.address.id
                    });
                }).then(function() {
                    return $scope.data.relationship.$save({
                        childId: ActiveCache.child.id, 
                        guardianId: $scope.data.guardian.id 
                    });
                }).then(function() {
                    $location.url('/child/' + ActiveCache.child.id + '/view');
                });
            };
        }
        
    ]);
