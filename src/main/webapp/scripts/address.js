'use strict';

angular.module('schoolApp')

    .service('addressService', ['$http', function ($http) {
        return {
            fetch: function (addressId) {
                return $http.get('api/address/' + addressId).then(function (response) {
                    return response.data;
                })
            },
            remove: function (addressId) {
                return $http.delete('api/address/' + addressId).then(function (response) {
                    return response.data;
                })
            },
            update: function (address) {
                return $http.put('api/address', address).then(function (response) {
                    return response.data;
                })
            },
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
                var unwatch = scope.$watch('address', function (newval) {
                    if (newval) {
                        var addressString = addressService.format(newval);
                        element.html(addressString);
                        unwatch();
                    }
                })
            }
        };
    }])

    .directive('inputAddress', ['statefulChildService', 'childService', 'addressService', 'uuid4',
        function (statefulChildService, childService, addressService, uuid4) {
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
                        scope.$watch('address.id', function (newval, oldval) {
                            if (typeof originalAddressId === "undefined" && newval !== "undefined") {
                                originalAddressId = newval;
                            }
                            console.log('Original Address ID: ', originalAddressId);
                            console.log('New Address ID: ', newval);
                            console.log('Old Address ID: ', oldval);
                            scope.viewData.commonAddress = (newval == statefulChildService.getScopedChildAddressId());
                        });

                        scope.$watch('viewData.commonAddress', function (newval) {
                            if (newval) {
                                console.log("Using child address: ", newval);
                                useChildAddress();
                            }  else {
                                console.log("NOT using child address: ", newval);
                            }

                            if (typeof originalAddressId !== "undefined") {
                                console.log(' * Original Address ID: ', originalAddressId);
                                addressService.fetch(originalAddressId).then(function(response) {
                                    scope.address = response;
                                }, function(error) {
                                    console.log('Address does not exist (initial). ');
                                    scope.address = { id: originalAddressId }
                                });
                            }
                        });
                    }

                    function useChildAddress() {
                        var childId = statefulChildService.getScopedChildId();
                        childService.fetch(childId).then(function (response) {
                            return addressService.fetch(response.addressId);
                        }).then(function (response) {
                            scope.address = response;
                        });
                    }
                }
            };
        }]);

