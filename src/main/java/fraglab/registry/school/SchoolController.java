package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolDao schoolDao;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public void post() {
        schoolService.init();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TreeElement> fetchSchoolTreeElements() {
        return schoolService.fetchSchoolTreeElements();
    }

    @RequestMapping(value = "/info/{childGroupId}", method = RequestMethod.GET)
    public SchoolData fetchSchoolData(@PathVariable String childGroupId) {
        return schoolService.fetchSchoolData(childGroupId);
    }

    @RequestMapping(value = "/group/{childGroupId}/statistics", method = RequestMethod.GET)
    public ChildGroupStatistics fetchChildGroupStatistics(@PathVariable String childGroupId) {
        return schoolService.fetchChildGroupStatistics(childGroupId);
    }

}
