'use strict';

angular.module('child')

    .controller('createChildController', ['$scope', 'ChildService', 'uuid4',
        function ($scope, ChildService,uuid4) {
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