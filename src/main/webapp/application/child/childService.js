'use strict';

angular.module('child')

    .service('ChildService', ['$rootScope', 'FoundationService', 'Child', 'Address', 
        function ($rootScope, FoundationService,  Child, Address) {

            var child = {};

            var cache = {
                child: {
                    id: null,
                    name: null
                }
            };

            function reset() {
				this.child = {};
				this.cache.child.id = null;
				this.cache.child.name = null;
            }

            function fetch(childId) {
            	var self = this;
				this.child = Child.get({id: childId});
				this.child.$promise.then(function(response) {
	                self.cache.child.name = formatName(response);
				});
                return this.child;
            }

            function save(child, address) {
                var self = this;
                Address.save(address).$promise.then(function (response) {
                	console.log("[ChildService] Saving child");
                    return Child.save({
                        addressId: address.id,
                        groupId: FoundationService.group.id
                    }, child).$promise;
                }).then(function (response) {
                	console.log("[ChildService] Saved child");
                    self.child = child;
                    $rootScope.toScopedChild();
                });
            }

            function remove(childId) {
                this.child = {};
                Child.remove({id: childId}).$promise.then(function (response) {
                    $rootScope.toChildList(FoundationService.group.id);
                });
            }
            
            function formatName (child) {
				var name = child.firstName + " ";
				if (child.callName) {
					name += "(" + child.callName + ") ";
				}
				return name + " " + child.lastName;
            }

            angular.extend(this, {
                child: child,
                cache: cache,
                reset: reset,
                fetch: fetch,
                save: save,
                remove: remove,
            });
        }
    ]);
