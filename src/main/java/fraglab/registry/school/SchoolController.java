package fraglab.registry.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        schoolDao.init();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TreeElement> fetchSchoolTreeElements() {
        return schoolService.fetchSchoolTreeElements();
    }

    @RequestMapping(value = "/info/{childGroupId}", method = RequestMethod.GET)
    public SchoolData fetchSchoolData(@PathVariable String childGroupId) {
        return schoolService.fetchSchoolData(childGroupId);
    }

    //////////////////////////////
    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public List<School> fetchSchools() {
        return schoolService.fetchSchools();
    }

    @RequestMapping(value = "/school", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSchool(@RequestBody School school) {
        schoolService.updateSchool(school);
    }

    @RequestMapping(value = "/school/{id}/classroom", method = RequestMethod.GET)
    public List<Classroom> fetchClassroomsForSchool(@PathVariable String id) {
        return schoolService.fetchClassroomsForSchool(id);
    }

    @RequestMapping(value = "/school/{id}/classroom", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClassroomForSchool(@PathVariable String id, @RequestBody Classroom classroom) {
        schoolService.updateClassroomForSchool(id, classroom);
    }

    @RequestMapping(value = "/school/term", method = RequestMethod.GET)
    public List<Term> fetchTerms() {
        return schoolService.fetchTerms();
    }

    @RequestMapping(value = "/school/term", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTerm(@RequestBody Term term) {
        schoolService.updateTerm(term);
    }

}
