'use strict';

angular.module('guardian')

    .controller('createGuardianController', ['$scope', 'uuid4', 'RelationshipService',
    
        function ($scope, uuid4, RelationshipService) {
            angular.extend($scope, {
                data: {
                    guardian: {
                        id: uuid4.generate(),
                        telephones: []
                    },
                    address: {
                        id: uuid4.generate()
                    },
                    relationship: {
                        id: uuid4.generate(),
                        metadata: {
                            type: null
                        }
                    }
                },
                viewData: {
                    submitLabel: "Δημιουργία",
                    sharedAddress: false
                }
            });

            $scope.submit = function () {
                RelationshipService.saveWithAddress($scope.data.address, $scope.data.guardian, $scope.data.relationship);
            };
        }
        
	]);