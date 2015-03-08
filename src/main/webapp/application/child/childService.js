'use strict';

angular.module('child')

    .service('ChildService', ['$rootScope', 'FoundationService', 'Child', 'AddressService',
        function ($rootScope, FoundationService,  Child, AddressService) {

            this.child = {};

            this.cache = {
                child: {
                    name: null
                }
            };

            this.reset = function() {
				this.child = {};
				this.cache.child.name = null;
            };

            this.fetch = function(childId) {
				this.child = Child.get({id: childId});
                return this.child;
            };

            this.save = function(child, address) {
                var self = this;
                AddressService.save(address).$promise.then(
                	function (response) {
						return Child.save({addressId: address.id, groupId: FoundationService.group.id}, child).$promise;
                	}
				).then(
					function (response) {
						self.child = child;
						$rootScope.toScopedChild();
					}
				);
            };

            this.remove = function(childId) {
                this.child = {};
                Child.remove({id: childId}).$promise.then(
					function (response) {
						$rootScope.toChildList(FoundationService.group.id);
					}
				);
            };
        }
    ]);
