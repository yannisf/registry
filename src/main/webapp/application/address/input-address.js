'use strict';

angular.module('schoolApp').directive('inputAddress', ['$rootScope', 'uuid4', 'ActiveCache', 'Address',
    function ($rootScope, uuid4, ActiveCache, Address) {
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
                    var id = scope.address.id;
                    scope.address = Address.getForPerson({personId: ActiveCache.child.id}, function() {
                        scope.address.id = id;
                    });
                };
            }
        };
    }]);
