package fraglab.registry.overview;

import fraglab.registry.child.Child;
import fraglab.registry.department.Department;
import fraglab.registry.group.Group;
import fraglab.registry.overview.meta.GroupStatistics;
import fraglab.registry.school.School;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/overview")
public class OverviewController extends BaseRestController {

    @Autowired
    private OverviewService overviewService;

    @RequestMapping(value = "security", method = RequestMethod.GET)
    public Authentication credentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @RequestMapping(value = "/school", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdateSchool(@RequestBody School school) {
        overviewService.createOrUpdateSchool(school);
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public List<School> fetchSchools() {
        return overviewService.fetchSchools();
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        overviewService.deleteSchool(id);
    }

    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public List<Department> fetchDepartmentsForSchool(@RequestParam(value = "schoolId", required = true) String schoolId) {
        return overviewService.fetchDepartmentsForSchool(schoolId);
    }

    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdateDepartmentForSchool(@RequestParam(value = "schoolId") String schoolId, @RequestBody Department department)
            throws NotFoundException {
        overviewService.createOrUpdateDepartmentForSchool(schoolId, department);
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDepartment(@PathVariable String id) {
        overviewService.deleteDepartment(id);
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public List<Group> fetchGroupsForDepartment(@RequestParam(value = "departmentId", required = true) String departmentId)
            throws NotFoundException {
        return overviewService.fetchGroupsForDepartment(departmentId);
    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdateGroup(@RequestBody Group group, @RequestParam(value = "departmentId") String departmentId)
            throws NotFoundException {
        overviewService.createOrUpdateGroupForDepartment(group, departmentId);
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteGroup(@PathVariable String id) {
        overviewService.deleteGroup(id);
    }

    @RequestMapping(value = "/group/{id}/child", method = RequestMethod.GET)
    public List<Child> fetchChildrenForGroup(@PathVariable String id) {
        return overviewService.fetchChildrenForGroup(id);
    }

    @RequestMapping(value = "/group/{id}/statistics", method = RequestMethod.GET)
    public GroupStatistics fetchChildGroupStatistics(@PathVariable String id) {
        return overviewService.fetchChildGroupStatistics(id);
    }

    @RequestMapping(value = "/group/{id}/info", method = RequestMethod.GET)
    public Map<String, Object> fetchGroupInfo(@PathVariable String id) throws NotFoundException {
        return overviewService.fetchGroupInfo(id);
    }

}
