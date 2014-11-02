'use strict';

angular.module('schoolApp')

    .factory('Address', ['$resource', function($resource) {
        return $resource('api/address/:addressId', {addressId: '@id'}, {
            save: {method: 'PUT', url: 'api/address'}
        });
    }])

    .service('addressService', ['$http', function ($http) {
        return {
            format: function (address) {
                var addressString = "";
                if (address.streetName) {
                    addressString = address.streetName;
                    if (address.streetNumber) {
                        addressString += " " + address.streetNumber;
                    }
                }
                if (address.neighbourhood) {
                    if (addressString.length > 0) {
                        addressString += ", " + address.neighbourhood;
                    } else {
                        addressString += address.neighbourhood;
                    }
                }
                if (address.postalCode) {
                    if (addressString.length > 0) {
                        addressString += ", " + address.postalCode;
                    } else {
                        addressString += address.postalCode;
                    }
                }
                if (address.city) {
                    if (addressString.length > 0) {
                        addressString += ", " + address.city;
                    } else {
                        addressString += address.city;
                    }
                }

                return addressString;
            },
            isBlank: function (address) {
                return ! (address.streetName
                    || address.streetNumber
                    || address.neighbourhood
                    || address.postalCode
                    || address.city);
            }
        }
    }])

    .directive('displayAddress', ['addressService', function (addressService) {
        return {
            restrict: 'E',
            scope: {
                address: "="
            },
            link: function (scope, element) {
                console.log("X")
                if (scope.address) {
                    console.log("Y")
                    scope.address.$promise.then(function(response) {
                        console.log("Z")
                        var addressString = addressService.format(scope.address);
                        element.html(addressString);
                    });
                }
            }
        };
    }])

    .directive('inputAddress', ['statefulChildService', 'Address', 'addressService', 'uuid4',
        function (statefulChildService, Address, addressService, uuid4) {
            return {
                restrict: 'E',
                scope: {
                    address: "=",
                    shareOption: "=",
                    typeaheads: "="
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
                                originalCommonAddress = (newval == statefulChildService.getScopedChildAddressId());
                                scope.viewData.commonAddress = originalCommonAddress;
                                unwatch();
                            }
                        });

                        scope.$watch('viewData.commonAddress', function (newval) {
                            if (newval && originalAddressId) {
                                scope.address = Address.get({addressId: statefulChildService.getScopedChildAddressId()});
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

