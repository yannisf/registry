package fraglab.school.grownup;

import fraglab.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrownupServiceImpl implements GrownupService {

    private static final Logger LOG = LoggerFactory.getLogger(GrownupServiceImpl.class);

    @Autowired
    GrownupDao grownupDao;

    @Override
    public void create(Grownup grownup) {
        grownupDao.create(grownup);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Grownup grownup = fetch(id);
        grownupDao.delete(grownup);
    }

    @Override
    public void update(Long id, Grownup grownup) throws NotFoundException {
        fetch(id);
        grownup.setId(id);
        grownupDao.update(grownup);
    }

    @Override
    public Grownup fetch(Long id) throws NotFoundException {
        Grownup grownup = grownupDao.fetch(id);
        if (grownup == null) {
            throw new NotFoundException("GrownUp not found");
        }

        return grownup;
    }

    @Override
    public List<Grownup> fetchAll() {
        return grownupDao.fetchAll();
    }

}
