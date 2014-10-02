package fraglab.school.guardian;

import fraglab.GenericDao;

import java.util.List;

public interface GuardianDao extends GenericDao<Guardian, Long> {

    List<Guardian> fetchAll();

}
