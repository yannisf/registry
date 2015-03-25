'use strict';

angular.module('schoolApp').directive('inputAddress', ['$rootScope', 'uuid4', 'ChildService', 'AddressService',
    function ($rootScope, uuid4, ChildService, AddressService) {
        return {
            replace: true,
            restrict: 'E',
            scope: {
                address: "=",
                allowCopy: "="
            },
            templateUrl: "application/address/input-address.tpl.html",
            link: function (scope) {
                scope.typeaheads = $rootScope.typeaheads;

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
