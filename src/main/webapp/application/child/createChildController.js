'use strict';

angular.module('child')
    .controller('createChildController', ['$location', '$scope', 'uuid4', 'ActiveCache', 'Child', 'Address',
        function ($location, $scope, uuid4, ActiveCache, Child, Address) {
            angular.extend($scope, {
                data: {
                    child: new Child({id: uuid4.generate()}),
                    address: new Address({id: uuid4.generate()})
                },
                viewData: {
                    submitLabel: 'Εισαγωγή'
                }
            });
            
            ActiveCache.child = null;

            $scope.submit = function() {
                $scope.data.address.$save(function () {
                    $scope.data.child.$save({addressId: $scope.data.address.id, groupId: ActiveCache.group.id},
                        function() {
                            ActiveCache.child = $scope.data.child;
                            $location.url('/child/' + ActiveCache.child.id + '/view');
                        }
                    );
                });
            };
            
            $scope.cancel = function() {
                $location.url('/group/' + ActiveCache.group.id);
            };
        }
    ]);
