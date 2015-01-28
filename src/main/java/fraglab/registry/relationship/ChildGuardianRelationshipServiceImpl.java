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
public class ChildGuardianRelationshipServiceImpl implements ChildGuardianRelationshipService {

    private static final Logger LOG = LoggerFactory.getLogger(ChildGuardianRelationshipServiceImpl.class);

    @Autowired
    ChildDao childDao;

    @Autowired
    GuardianService guardianService;

    @Autowired
    AddressService addressService;

    @Autowired
    ChildGuardianRelationshipDao childGuardianRelationshipDao;

    @Override
    public ChildGuardianRelationship fetch(String id) throws NotFoundException {
        return childGuardianRelationshipDao.fetch(id);
    }

    @Override
    public ChildGuardianRelationship fetch(String childId, String guardianId) throws NotFoundException {
        return childGuardianRelationshipDao.fetchForChildAndGuardian(childId, guardianId);
    }

    @Override
    public List<ChildGuardianRelationship> fetchRelationships(String childId) throws NotFoundException {
        List<ChildGuardianRelationship> relationships = childGuardianRelationshipDao.fetchAllForChild(childId);
        LOG.debug("Fetched {} relationships for child {}", relationships.size(), childId);

        for (ChildGuardianRelationship relationship : relationships) {
            relationship.setGuardian(guardianService.fetch(relationship.getGuardianId()));
        }

        Collections.sort(relationships);
        return relationships;
    }

    @Override
    @Transactional
    public void delete(String id) throws NotFoundException {
        ChildGuardianRelationship childGuardianRelationship = fetch(id);
        childGuardianRelationshipDao.delete(childGuardianRelationship);
        //Delete Guardian as long as sharing guardians is not implemented
        guardianService.delete(childGuardianRelationship.getGuardianId());
    }

    @Override
    @Transactional
    public void updateRelationshipWithGuardianAndAddress(RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress) {
        guardianAddressHousekeeping(relationshipWithGuardianAndAddress.getGuardian(), relationshipWithGuardianAndAddress.getAddress());
        addressService.update(relationshipWithGuardianAndAddress.getAddress());
        guardianService.update(relationshipWithGuardianAndAddress.getGuardian());
        childGuardianRelationshipDao.update(relationshipWithGuardianAndAddress.getRelationship());

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
