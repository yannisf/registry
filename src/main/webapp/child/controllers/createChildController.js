define(['angular', 'currentModule', 'templateCache!../edit.html', 'scripts/vendor/angular-uuid4/angular-uuid4'],
function(angular, currentModule) {
	'use strict';

    currentModule.addDependencies(['uuid4']).controller('createChildController', ['$scope', 'childService', 'statefulChildService', 'addressService', 'uuid4', '$http',
        function ($scope, childService, statefulChildService, addressService, uuid4, $http) {
            angular.extend($scope, {
                data: {
                    child: {
                        id: uuid4.generate()
                    },
                    address: {
                        id: uuid4.generate()
                    }
                },
                viewData: {
                    submitLabel: 'Εισαγωγή'
                },
                invoke: function($event, message) {
                    console.log(message);
                    $event.preventDefault();
                }
            });

            $scope.submit = function () {
                addressService.update($scope.data.address).then(function (response) {
                    $scope.data.child.addressId = $scope.data.address.id;
                    return childService.update($scope.data.child);
                }).then(function (response) {
                    var childId = response.id;
                    statefulChildService.setScopedChildId(childId);
                    statefulChildService.setScopedChildAddressId($scope.data.child.addressId)
                    $scope.toScopedChild();
                });
            }
        }
    ]);
});
