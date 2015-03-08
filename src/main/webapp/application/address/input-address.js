'use strict';

angular.module('schoolApp')

    .directive('inputAddress', ['ChildService', 'AddressService', 'uuid4',
        function (ChildService, AddressService, uuid4) {
            return {
                replace: true,
                restrict: 'E',
                scope: {
                    address: "=",
                    allowCopy: "=",
                    typeaheads: "="
                },
                templateUrl: "application/address/input-address.tpl.html",
                link: function (scope) {

                    scope.viewData = {
                        isAddressOpen: false
                    };

                    scope.clear = function() {
                        scope.address = {id: scope.address.id};
                    };

                    scope.copyFromChild = function() {
                        AddressService.getForPerson(ChildService.child.id).$promise.then(
                            function(response) {
                                response.id = scope.address.id;
                                 scope.address = response;
                            }
                        );
                    };
                }
            };
        }]);
