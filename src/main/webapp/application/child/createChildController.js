'use strict';

angular.module('child')

    .controller('createChildController', ['$scope', 'uuid4', 'ChildService', 'Address',
        function ($scope, uuid4, ChildService, Address) {
            angular.extend($scope, {
                data: {
                    child: {
                        id: uuid4.generate(),
                    },
                    address: new Address({id: uuid4.generate()})
                },
                viewData: {
                    submitLabel: 'Εισαγωγή'
                }
            });

            $scope.submit = function() {
                ChildService.save($scope.data.child, $scope.data.address);
            };
        }]);