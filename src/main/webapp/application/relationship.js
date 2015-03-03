'use strict';

angular.module('relationship', ['ngResource'])

    .factory('Relationship', ['$resource', function($resource) {
        return $resource('api/relationship/:id', {}, {
            fetchRelationship: {method:'GET', url:'api/relationship/child/:childId/guardian/:guardianId'},
            fetchRelationships: {method:'GET', url:'api/relationship/child/:childId', isArray: true},
            save: {method: 'PUT', url: 'api/relationship/child/:childId/guardian/:guardianId'},
            remove: {method: 'DELETE', url: 'api/relationship/:relationshipId'}
        });
    }])

	.service('RelationshipService', ['ChildService', 'Relationship', 'Guardian', 'Address',
		function (ChildService, Relationship, Guardian, Address) {
		
			var fetchRelationships = function(childId) {
				//console.log('[RelationshipService] Fetching relationships for childId: ', ChildService.child.id);
				//return Relationship.fetchRelationships({childId: ChildService.child.id});
				console.log('[RelationshipService] Fetching relationships for childId: ', childId);
				return Relationship.fetchRelationships({childId: childId});
			};

			var saveWithAddress = function (addressId, guardian, relationship, callback) {
				console.log('[RelationshipService] Saving with address');
				console.log('[RelationshipService] Child seems to be ', ChildService.child.id);
				return Guardian.save({addressId: addressId}, guardian).$promise.then(function() {
					console.log('[RelationshipService] Child id is ', ChildService.child.id);
					return Relationship.save({
						childId: ChildService.child.id,
						guardianId: guardian.id
					}, relationship);
				}).then(function() {
					console.log('callback is ', callback);
					callback();
				});
			};

			var saveWithoutAddress = function (address, guardian, relationship, callback) {
				console.log('[RelationshipService] Saving without address');
				Address.save(address).$promise.then(function(response) {
					return saveWithAddress(address.id, guardian, relationship, callback);
				});
			};
			
			return {
				fetchRelationships: fetchRelationships,
				saveWithAddress: saveWithAddress,
				saveWithoutAddress: saveWithoutAddress
			};
		}
	]);

