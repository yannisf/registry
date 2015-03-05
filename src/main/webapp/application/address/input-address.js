'use strict';

angular.module('schoolApp')

    .directive('inputAddress', ['ChildService', 'Address', 'AddressService', 'uuid4',
        function (ChildService, Address, AddressService, uuid4) {
            return {
                restrict: 'E',
                scope: {
                    address: "=",
                    shareOption: "=",
                    typeaheads: "="
                },
                templateUrl: "application/address/input-address.tpl.html",
                link: function (scope) {

                    console.log("Address is ", scope.address);
                    scope.viewData = {
                        commonAddress: false,
                        isAddressOpen: false
                    };

                    if (scope.shareOption) {

                        var unwatch = scope.$watch('address.id', 
                            function (newval, oldval) {
                                console.log('[inputAddress] address.id new value ', newval);
                                console.log('[inputAddress] address.id old value ', oldval);
                                Address.getForPerson({personId: ChildService.child.id}).$promise.then(
                                    function(response) {
                                        if (newval == response.id) {
                                            console.log('[inputAddress] address is common');
                                            scope.viewData.commonAddress = true;
                                        }
                                    }
                                );
                                unwatch();
                            }
                        );

                        scope.$watch('viewData.commonAddress', 
                            function (newval, oldval) {
                                console.log('[inputAddress] commonAddress new value ', newval);
                                console.log('[inputAddress] commonAddress old value ', oldval);
                                if (newval) {
                                    scope.address = Address.getForPerson({personId: ChildService.child.id});
                                } else {
                                    scope.address = {id: uuid4.generate()}
                                }
                            }
                        );
                    }
                }
            };
        }]);

