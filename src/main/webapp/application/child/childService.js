'use strict';

angular.module('child')

    .service('ChildService', ['$rootScope', 'FoundationService', 'Child', 'Address', 
        function ($rootScope, FoundationService,  Child, Address) {
            var cache = {
                child: {
                    id: null,
                    name: null
                }
            };
            var child = {};
            var setChild = function(newChild) {
                console.log('Setting child: ', newChild);
                console.log('The ids are ', FoundationService.groupChildrenIds);
                this.child = newChild;
                cache.child.name = formatName(newChild);
            };
            var reset = function() {
                child = {};
                cache.child.id = null;
                cache.child.name = null;
            };
            var fetch = function(childId) {
                return Child.get({id: childId});
            };
            var save = function(child, address) {
                var self = this;
                Address.save(address).$promise.then(function (response) {
                	console.log("[ChildService] Saving child");
                    return Child.save({
                        addressId: address.id,
                        groupId: FoundationService.group.id
                    }, child);
                }).then(function (response) {
                	console.log("[ChildService] Saved child");
                    self.child = child;
                    $rootScope.toScopedChild();
                });
            };
            var remove = function(childId) {
                var self = this;
                Child.remove({id: childId}).$promise.then(function (response) {
                    self.child = {};
                    $rootScope.toChildList(FoundationService.group.id);
                });
            };
            
            function formatName(child) {
                var name = child.firstName + " ";
                if (child.callName) {
                    name += "(" + child.callName + ") ";
                }
                return name + " " + child.lastName;
            }
            
            return {
                cache: cache,
                child: child,
                reset: reset,
                fetch: fetch,
                save: save,
                remove: remove,
                setChild: setChild,
            };
        }
    ]);
