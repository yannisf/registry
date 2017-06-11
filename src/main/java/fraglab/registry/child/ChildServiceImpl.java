package fraglab.registry.child;

import fraglab.registry.address.AddressService;
import fraglab.registry.group.Group;
import fraglab.registry.group.GroupJpaRepository;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.relationship.Relationship;
import fraglab.registry.relationship.RelationshipType;
import fraglab.web.NotIdentifiedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Predicate;

@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildJpaRepository childJpaRepository;

    @Autowired
    private ChildPhotoJpaRepository childPhotoJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public Optional<Child> find(String id) {
        return Optional.ofNullable(childJpaRepository.findOne(id));
    }

    @Override
    public Child save(Child child) {
        if (StringUtils.isBlank(child.getId())) {
            throw new NotIdentifiedException();
        }

        Child childToSave = null;
        Child foundChild = childJpaRepository.findOne(child.getId());
        if (foundChild != null) {
            bindSubmittedToFound(child, foundChild);
            childToSave = foundChild;
        } else {
            childToSave = child;
        }

        Child savedChild = childJpaRepository.save(childToSave);
        updateGroupMembersNum(savedChild.getGroup());

        return savedChild;
    }

    private void bindSubmittedToFound(Child submitted, Child persisted) {
        persisted.setFirstName(submitted.getFirstName());
        persisted.setLastName(submitted.getLastName());
        persisted.setCallName(submitted.getCallName());
        persisted.setDateOfBirth(submitted.getDateOfBirth());
        persisted.setLevel(submitted.getLevel());
        persisted.setGender(submitted.getGender());
        persisted.setNationality(submitted.getNationality());
        persisted.setNotes(submitted.getNotes());
    }

    @Override
    public Child save(Child child, String addressId, String groupId) {
        addressService.find(addressId).ifPresent(child::setAddress);
        child.setGroup(groupJpaRepository.findOne(groupId));
        return save(child);
    }

    @Override
    public void delete(String id) {
        find(id).ifPresent(c -> {
            childJpaRepository.delete(c);
            updateGroupMembersNum(c.getGroup());
        });
    }

    @Override
    public Optional<Child> findWithRelationships(String id) {
        return Optional.of(childJpaRepository.queryForRelationships(id));
    }

    @Override
    public Map<String, Map<String, String>> findEmailsForGroup(String groupId) {
        Map<String, Map<String, String>> childEmailContacts = new HashMap<>();

        List<Child> childrenInGroup = childJpaRepository.findByGroupId(groupId);

        Predicate<Relationship> isFather = (r -> r.getMetadata().getType() == RelationshipType.FATHER);
        Predicate<Relationship> isMother = (r -> r.getMetadata().getType() == RelationshipType.MOTHER);
        Predicate<Relationship> hasEmail = (r -> r.getGuardian().getEmail() != null);

        childrenInGroup.forEach(c -> c.getRelationships().stream()
                .filter(isFather.or(isMother))
                .filter(hasEmail)
                .forEach(r -> {
                    childEmailContacts.putIfAbsent(c.getReportName(), new HashMap<>());
                    Guardian g = r.getGuardian();
                    childEmailContacts.get(c.getReportName()).put(g.getFullName(), g.getEmail());
                }));

        return childEmailContacts;
    }

    @Override
    public String emailsForGroup(String groupId) {
        Template template = velocityEngine.getTemplate("/templates/group_contacts.vm", "UTF-8");
        VelocityContext context = new VelocityContext();
        context.put("childEmailContacts", findEmailsForGroup(groupId));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();

    }

    @Override
    public Optional<ChildPhoto> findChildPhoto(String id) {
        ChildPhoto childPhoto = childPhotoJpaRepository.findOne(id);
        if (childPhoto != null) {
            return Optional.ofNullable(childPhoto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteChildPhoto(String id) {
        Child child = childJpaRepository.findOne(id);
        if (child != null) {
            child.setPhoto(null);
        }
    }

    private void updateGroupMembersNum(Group group) {
        groupJpaRepository.queryForUpdateMemberCount(group);
    }

    @Override
    public String saveChildPhoto(String childId, byte[] bytes) {
        Optional<Child> child = find(childId);
        String hex = null;

        if (child.isPresent()) {
            byte[] resizedBytes = getResizedBytes(bytes);
            hex = DigestUtils.md5DigestAsHex(resizedBytes);
            ChildPhoto childPhoto = new ChildPhoto();
            childPhoto.setId(hex);
            childPhoto.setContent(resizedBytes);
            child.get().setPhoto(childPhoto);
            save(child.get());
        }

        return hex;
    }

    private byte[] getResizedBytes(byte[] bytes) throws RuntimeException {
        BufferedImage resized = Scalr.resize(createImageFromBytes(bytes), 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(resized, "jpg", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
