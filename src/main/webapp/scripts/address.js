'use strict';

angular.module('schoolApp')

    .factory('Address', ['$resource', function($resource) {
        return $resource('api/address/:addressId', { }, {
            save: {method: 'PUT', url: 'api/address'}
        });
    }])

    .service('addressService', [function () {
        return {
            format: function (address) {
                var formattedAddress = "";
                if (address.streetName) {
                    formattedAddress = address.streetName;
                    if (address.streetNumber) {
                        formattedAddress += " " + address.streetNumber;
                    }
                }
                if (address.neighbourhood) {
                    if (formattedAddress.length > 0) {
                        formattedAddress += ", " + address.neighbourhood;
                    } else {
                        formattedAddress += address.neighbourhood;
                    }
                }
                if (address.postalCode) {
                    if (formattedAddress.length > 0) {
                        formattedAddress += ", " + address.postalCode;
                    } else {
                        formattedAddress += address.postalCode;
                    }
                }
                if (address.city) {
                    if (formattedAddress.length > 0) {
                        formattedAddress += ", " + address.city;
                    } else {
                        formattedAddress += address.city;
                    }
                }

                return formattedAddress;
            },
            isBlank: function (address) {
                return !(address
                    && (address.streetName
                        || address.streetNumber
                        || address.neighbourhood
                        || address.postalCode
                        || address.city));
            }
        }
    }])

    .filter('addressFilter', ['addressService', function (addressService) {
        return function (address) {
            if (address) {
                return addressService.format(address);
            }
        }
    }])

    .directive('inputAddress', ['ChildService', 'Address', 'addressService', 'uuid4',
        function (ChildService, Address, addressService, uuid4) {
            return {
                restrict: 'E',
                scope: {
                    address: "=",
                    shareOption: "=",
                },
                templateUrl: "templates/input-address.html",
                link: function (scope) {

                    scope.viewData = {
                        commonAddress: false
                    };

                    if (scope.shareOption) {
                        var originalAddressId;
                        var originalCommonAddress = false;

                        var unwatch = scope.$watch('address.id', function (newval) {
                            if (newval) {
                                originalAddressId = newval;
                                originalCommonAddress = (newval == ChildService.child.addressId);
                                scope.viewData.commonAddress = originalCommonAddress;
                                unwatch();
                            }
                        });

                        scope.$watch('viewData.commonAddress', function (newval) {
                            if (newval && originalAddressId) {
                                scope.address = Address.get({addressId: ChildService.child.addressId});
                            } else if (originalCommonAddress) {
                                scope.address = {
                                    id: uuid4.generate()
                                }
                            } else if (originalAddressId) {
                                if (addressService.isBlank(scope.address)) {
                                    scope.address = {
                                        id: originalAddressId
                                    }
                                } else {
                                    scope.address = Address.get({addressId: originalAddressId});
                                }
                            }
                        });
                    }
                }
            };
        }]);

