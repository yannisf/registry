package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolDao schoolDao;

    @RequestMapping(method = RequestMethod.POST)
    public void post() {
        schoolDao.execute();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SchoolTreeElement> get() {
        return schoolDao.select();
    }

}
