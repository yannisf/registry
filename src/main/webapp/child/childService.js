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
            var setChild = function(child) {
                this.child = child;
                cache.child.name = formatName(child);
            };
            var save = function(child, address) {
                var self = this;
                Address.save(address).$promise.then(function (response) {
                    return Child.save({
                        addressId: address.id,
                        groupId: FoundationService.group.id
                    }, child);
                }).then(function (response) {
                    console.log("Saved child");
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

            }
            
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
                save: save,
                remove: remove,
                setChild: setChild,
            };
        }
    ]);
