'use strict';

angular.module('schoolApp')

    .factory('Address', ['$resource', function($resource) {
        return $resource('api/address/:addressId', { }, {
            save: {method: 'PUT', url: 'api/address'},
            getForPerson: {method: 'GET', url: 'api/address/person/:personId'}
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
                return !(address && (
                	address.streetName ||
                	address.streetNumber ||
                	address.neighbourhood ||
                	address.postalCode ||
                	address.city));
            }
        };
    }])

    .filter('addressFilter', ['addressService', function (addressService) {
        return function (address) {
            if (address) {
                return addressService.format(address);
            }
        };
    }]);
