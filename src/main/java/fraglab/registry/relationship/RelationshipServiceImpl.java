package fraglab.registry.relationship;

import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.registry.child.ChildDao;
import fraglab.registry.formobject.RelationshipWithGuardianAndAddress;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.guardian.GuardianService;
import fraglab.web.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    private static final Logger LOG = LoggerFactory.getLogger(RelationshipServiceImpl.class);

    @Autowired
    ChildDao childDao;

    @Autowired
    GuardianService guardianService;

    @Autowired
    AddressService addressService;

    @Autowired
    RelationshipDao relationshipDao;

    @Override
    public Relationship fetch(String id) throws NotFoundException {
        return relationshipDao.fetch(id);
    }

    @Override
    public Relationship fetch(String childId, String guardianId) throws NotFoundException {
        return relationshipDao.fetchForChildAndGuardian(childId, guardianId);
    }

    @Override
    public List<Relationship> fetchRelationships(String childId) throws NotFoundException {
        List<Relationship> relationships = relationshipDao.fetchAllForChild(childId);
        LOG.debug("Fetched {} relationships for child {}", relationships.size(), childId);

        for (Relationship relationship : relationships) {
            relationship.setGuardian(guardianService.fetch(relationship.getGuardianId()));
        }

        Collections.sort(relationships);
        return relationships;
    }

    @Override
    @Transactional
    public void delete(String id) throws NotFoundException {
        Relationship relationship = fetch(id);
        relationshipDao.delete(relationship);
        //Delete Guardian as long as sharing guardians is not implemented
        guardianService.delete(relationship.getGuardianId());
    }

    @Override
    @Transactional
    public void updateRelationshipWithGuardianAndAddress(RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress) {
        guardianAddressHousekeeping(relationshipWithGuardianAndAddress.getGuardian(), relationshipWithGuardianAndAddress.getAddress());
        addressService.update(relationshipWithGuardianAndAddress.getAddress());
        guardianService.update(relationshipWithGuardianAndAddress.getGuardian());
        relationshipDao.update(relationshipWithGuardianAndAddress.getRelationship());

    }

    private void guardianAddressHousekeeping(Guardian guardian, Address address) {
        try {
            String updatedGuardianAddressId = address.getId();
            guardian.setAddressId(updatedGuardianAddressId);
            Guardian retrievedGuardian = guardianService.fetch(guardian.getId());
            String retrievedGuardianAddressId = retrievedGuardian.getAddressId();
            if (StringUtils.isNotBlank(retrievedGuardianAddressId)
                    && !retrievedGuardianAddressId.equals(updatedGuardianAddressId)
                    && !addressService.isSharedAddress(retrievedGuardianAddressId)) {
                addressService.delete(retrievedGuardianAddressId);
            }
        } catch (NotFoundException e) {
            LOG.trace("Address housekeeping does not apply to new guardian records. ");
        }
    }

}
