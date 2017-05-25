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
        Child savedChild = childJpaRepository.save(child);
        updateGroupMembersNum(child.getGroup());

        return savedChild;
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
        Child child = childJpaRepository.getById(id);
        if (child != null) {
            return Optional.ofNullable(child.getPhoto());
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
    public void saveChildPhoto(String childId, byte[] bytes) {
        Optional<Child> child = find(childId);

        if (child.isPresent()) {
            byte[] resizedBytes = getResizedBytes(bytes);
            String hex = DigestUtils.md5DigestAsHex(resizedBytes);
            ChildPhoto childPhoto = new ChildPhoto();
            childPhoto.setId(UUID.randomUUID().toString());
            childPhoto.setContent(resizedBytes);
            childPhoto.setMd5(hex);
            child.get().setPhoto(childPhoto);
            save(child.get());
        }
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
