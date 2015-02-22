'use strict';

angular.module('child')

    .controller('createChildController', ['$scope', 'Flash', 'Child', 'ChildService', 'SchoolService', 'Address',  'uuid4',
        function ($scope, Flash, Child, ChildService, SchoolService, Address, uuid4) {
            angular.extend($scope, {
                data: {
                    child: {
                        id: uuid4.generate(),
                        childGroupId: SchoolService.childGroupId
                    },
                    address: {
                        id: uuid4.generate()
                    }
                },
                viewData: {
                    submitLabel: 'Εισαγωγή'
                }
            });

            $scope.submit = function () {
                var childWithAddress = {
                    child: $scope.data.child,
                    address: $scope.data.address
                };

                Child.saveWithAddress(childWithAddress).$promise.then(function (response) {
                    ChildService.childIds = [];
                    ChildService.child = $scope.data.child;
                    Flash.setSuccessMessage("Επιτυχής καταχώρηση.");
                    $scope.toScopedChild();
                }, function (response) {
                    Flash.setWarningMessage("Σφάλμα καταχώρησης.");
                });
            };
        }]);