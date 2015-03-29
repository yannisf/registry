'use strict';

angular.module('relationship', ['ngResource'])

    .factory('Relationship', ['$resource', function($resource) {
        return $resource('api/relationship/:id', {}, {
            fetchRelationship: {method:'GET', url:'api/relationship/child/:childId/guardian/:guardianId'},
            fetchRelationships: {method:'GET', url:'api/relationship/child/:childId', isArray: true},
            save: {method: 'PUT', url: 'api/relationship/child/:childId/guardian/:guardianId'},
            remove: {method: 'DELETE', url: 'api/relationship/:id'}
        });
    }])

	.service('RelationshipService', ['$rootScope', 'ActiveCache', 'Relationship', 'Guardian', 'Address',
		function ($rootScope, ActiveCache, Relationship, Guardian, Address) {
		
			var fetchRelationships = function(childId) {
				return Relationship.fetchRelationships({childId: childId});
			};

			var saveWithAddress = function (address, guardian, relationship) {
				Address.save(address).$promise.then(
					function() {
						return Guardian.save({addressId: address.id}, guardian).$promise;
					}
				).then(
					function() {
						return Relationship.save({childId: ActiveCache.child.id, guardianId: guardian.id }, relationship).$promise;
					}
				).then(
					function() {
						$rootScope.toScopedChild();
					}
				);
			};

			return {
				fetchRelationships: fetchRelationships,
				saveWithAddress: saveWithAddress
			};
		}
	]);
