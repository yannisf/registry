package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.address.AddressService;
import fraglab.school.child.ChildDao;
import fraglab.school.formobject.RelationshipWithGuardianAndAddress;
import fraglab.school.guardian.Guardian;
import fraglab.school.guardian.GuardianService;
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
        guardianAddressHousekeeping(relationshipWithGuardianAndAddress.getGuardian());
        addressService.update(relationshipWithGuardianAndAddress.getAddress());
        guardianService.update(relationshipWithGuardianAndAddress.getGuardian());
        childGuardianRelationshipDao.update(relationshipWithGuardianAndAddress.getRelationship());

    }

    private void guardianAddressHousekeeping(Guardian guardian) {
        try {
            Guardian retrievedGuardian = guardianService.fetch(guardian.getId());
            String retrievedGuardianAddressId = retrievedGuardian.getAddressId();
            String updatedGuardianAddressId = guardian.getAddressId();
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
