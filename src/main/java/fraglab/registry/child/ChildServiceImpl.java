package fraglab.registry.child;

import fraglab.registry.address.Address;
import fraglab.registry.address.AddressService;
import fraglab.registry.group.Group;
import fraglab.registry.group.GroupJpaRepository;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildJpaRepository childJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public void delete(String id) throws NotFoundException {
        Child child = find(id);
        childJpaRepository.delete(child);
        updateGroupMembersNum(child.getGroup());
    }

    @Override
    public void save(Child child) throws NotIdentifiedException {
        if (StringUtils.isBlank(child.getId())) {
            throw new NotIdentifiedException();
        }

        childJpaRepository.save(child);
        updateGroupMembersNum(child.getGroup());
    }

    @Override
    public void save(Child child, String addressId, String groupId) throws NotIdentifiedException, NotFoundException {
        Address address = addressService.find(addressId);
        Group group = groupJpaRepository.findOne(groupId);
        child.setAddress(address);
        child.setGroup(group);
        save(child);
    }

    @Override
    public Child find(String id) throws NotFoundException {
        Child child = childJpaRepository.findOne(id);
        if (child == null) {
            throw new NotFoundException("Child not found");
        }

        return child;
    }

    @Override
    public Child findWithRelationships(String id) {
        return childJpaRepository.queryForRelationships(id);
    }

    private void updateGroupMembersNum(Group group) {
        groupJpaRepository.queryForUpdateMemberCount(group);
    }

}
