'use strict';

angular.module('child')

    .service('ChildService', ['$rootScope', 'Child', 'Address', 'ActiveCache',
        function ($rootScope, Child, Address, ActiveCache) {

            this.fetch = function(childId) {
				ActiveCache.child = Child.get({id: childId});
                return ActiveCache.child;
            };

            this.save = function(child, address) {
                address.$save(function () {
                    return Child.save({addressId: address.id, groupId: ActiveCache.group.id}, child).$promise;
                }).then(function () {
                    ActiveCache.child = child;
                    $rootScope.toScopedChild();
                });
            };

            this.remove = function(childId) {
                Child.remove({id: childId}).$promise.then(function (response) {
                    ActiveCache.child = null;
                    $rootScope.toChildList(ActiveCache.group.id);
                });
            };
        }
    ]);
