'use strict';

angular.module('guardian')

    .controller('createGuardianController', ['$location', '$scope', 'uuid4', 'ActiveCache', 'Guardian', 'Address', 'Relationship',
    
        function ($location, $scope, uuid4, ActiveCache, Guardian, Address, Relationship) {
            angular.extend($scope, {
                data: {
                    guardian: new Guardian( { id: uuid4.generate(), telephones: [] } ),
                    address: new Address( { id: uuid4.generate() } ),
                    relationship: new Relationship( { id: uuid4.generate(), metadata: { type: null } } )
                },
                viewData: {
                    submitLabel: "Δημιουργία",
                    sharedAddress: false
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