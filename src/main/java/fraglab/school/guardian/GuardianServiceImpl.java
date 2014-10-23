package fraglab.school.guardian;

import fraglab.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuardianServiceImpl implements GuardianService {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianServiceImpl.class);

    @Autowired
    GuardianDao guardianDao;

    @Override
    public void delete(String id) throws NotFoundException {
        Guardian guardian = fetch(id);
        guardianDao.delete(guardian);
    }

    @Override
    public void update(Guardian guardian) throws NotFoundException {
        guardianDao.update(guardian);
    }

    @Override
    public Guardian fetch(String id) throws NotFoundException {
        Guardian guardian = guardianDao.fetch(id);
        if (guardian == null) {
            throw new NotFoundException("Guardian not found");
        }

        return guardian;
    }

}
