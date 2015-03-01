'use strict';

angular.module('child')

    .controller('createChildController', ['$scope', 'uuid4', 'ChildService',
        function ($scope, uuid4, ChildService) {
            angular.extend($scope, {
                data: {
                    child: {
                        id: uuid4.generate(),
                    },
                    address: {
                        id: uuid4.generate()
                    }
                },
                viewData: {
                    submitLabel: 'Εισαγωγή'
                }
            });

            $scope.submit = function() {
                ChildService.save($scope.data.child, $scope.data.address);
            };
        }]);