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
        schoolDao.execute();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SchoolTreeElement> fetchSchoolTreeElements() {
        return schoolService.fetchSchoolTreeElements();
    }

    @RequestMapping(value = "/info/{yearClassId}", method = RequestMethod.GET)
    public SchoolData yearClassInformation(@PathVariable String yearClassId) {
        return schoolService.fetchSchoolData(yearClassId);
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

    @RequestMapping(value = "/school/{id}/class", method = RequestMethod.GET)
    public List<SchoolClass> fetchClassesForSchool(@PathVariable String id) {
        return schoolService.fetchClassesForSchool(id);
    }

    @RequestMapping(value = "/school/{id}/class", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClassForSchool(@PathVariable String id, @RequestBody SchoolClass schoolClass) {
        schoolService.updateClassForSchool(id, schoolClass);
    }

    @RequestMapping(value = "/school/year", method = RequestMethod.GET)
    public List<SchoolYear> fetchYears() {
        return schoolService.fetchYears();
    }

    @RequestMapping(value = "/school/year", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateYear(@RequestBody SchoolYear year) {
        schoolService.updateYear(year);
    }

}
