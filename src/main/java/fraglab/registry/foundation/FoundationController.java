package fraglab.registry.foundation;

import fraglab.registry.child.Child;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.foundation.meta.GroupStatistics;
import fraglab.registry.foundation.meta.TreeElement;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foundation")
public class FoundationController {

    @Autowired
    private FoundationService foundationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TreeElement> fetchSchoolTreeElements() {
        return foundationService.fetchSchoolTreeElements();
    }

    @RequestMapping(value = "/group/{groupId}/info", method = RequestMethod.GET)
    public GroupDataTransfer fetchSchoolData(@PathVariable String groupId) {
        return foundationService.fetchSchoolData(groupId);
    }

    @RequestMapping(value = "/group/{groupId}/statistics", method = RequestMethod.GET)
    public GroupStatistics fetchChildGroupStatistics(@PathVariable String groupId) {
        return foundationService.fetchChildGroupStatistics(groupId);
    }

    @RequestMapping(value = "/group/{groupId}/children", method = RequestMethod.GET)
    public List<Child> fetchChildGroup(@PathVariable String groupId) {
        return foundationService.fetchChildrenForGroup(groupId);
    }

    @RequestMapping(value = "/school", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdateSchool(@RequestBody School school) {
        foundationService.createOrUpdateSchool(school);
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public List<School> fetchSchools() {
        return foundationService.fetchSchools();
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        foundationService.deleteSchool(id);
    }

    @RequestMapping(value = "/school/{schoolId}/classroom", method = RequestMethod.GET)
    public List<Classroom> fetchClassroomsForSchool(@PathVariable String schoolId) {
        return foundationService.fetchClassroomsForSchool(schoolId);
    }

    @RequestMapping(value = "/school/{schoolId}/classroom", method = RequestMethod.PUT)
    public void createOrUpdateClassroomForSchool(@PathVariable String schoolId, @RequestBody Classroom classroom) {
        foundationService.createOrUpdateClassroomForSchool(schoolId, classroom);
    }

}
