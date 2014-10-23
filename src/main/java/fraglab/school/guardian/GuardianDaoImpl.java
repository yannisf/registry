package fraglab.school.guardian;

import fraglab.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class GuardianDaoImpl extends GenericDaoImpl<Guardian, String> implements GuardianDao {

}
