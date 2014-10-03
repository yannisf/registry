package fraglab.school.relationship;

import fraglab.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RelationshipDaoImpl extends GenericDaoImpl<ChildGuardianRelationship, Long> implements RelationshipDao {
}
