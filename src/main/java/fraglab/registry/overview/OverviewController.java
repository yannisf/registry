package fraglab.registry.overview;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.department.Department;
import fraglab.registry.group.Group;
import fraglab.registry.group.GroupStatistics;
import fraglab.registry.school.School;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/overview")
public class OverviewController extends BaseRestController {

    @Autowired
    private OverviewService overviewService;

    @Autowired
    private ChildService childService;

    @RequestMapping(value = "security", method = RequestMethod.GET)
    public Authentication credentials() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = "/school", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveSchool(@RequestBody School school) {
        overviewService.saveSchool(school);
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public List<School> findSchools() {
        return overviewService.findSchools();
    }

    @RequestMapping(value = "/school/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteSchool(@PathVariable String id) {
        overviewService.deleteSchool(id);
    }

    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public List<Department> findDepartmentsForSchool(@RequestParam(value = "schoolId", required = true) String schoolId) {
        return overviewService.findDepartmentsForSchool(schoolId);
    }

    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveDepartmentForSchool(@RequestParam(value = "schoolId") String schoolId,
                                        @RequestBody Department department) {
        overviewService.saveDepartmentForSchool(schoolId, department);
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDepartment(@PathVariable String id) {
        overviewService.deleteDepartment(id);
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public List<Group> findGroupsForDepartment(@RequestParam(value = "departmentId", required = true) String departmentId) {
        return overviewService.findGroupsForDepartment(departmentId);
    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveGroup(@RequestBody Group group, @RequestParam(value = "departmentId") String departmentId) {
        overviewService.saveGroupForDepartment(group, departmentId);
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteGroup(@PathVariable String id) {
        overviewService.deleteGroup(id);
    }

    @RequestMapping(value = "/group/{id}/child", method = RequestMethod.GET)
    public List<Child> findChildrenForGroup(@PathVariable String id) {
        return overviewService.findChildrenForGroup(id);
    }

    @RequestMapping(value = "/group/{id}/statistics", method = RequestMethod.GET)
    public GroupStatistics findChildGroupStatistics(@PathVariable String id) {
        return overviewService.findChildGroupStatistics(id);
    }

    @RequestMapping(value = "/group/{id}/info", method = RequestMethod.GET)
    public Map<String, Object> findGroupInfo(@PathVariable String id) throws NotFoundException {
        return overviewService.findGroupInfo(id);
    }

    @RequestMapping(value = "/group/{groupId}/emails", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public void findGroupEmails(@PathVariable String groupId, HttpServletResponse response) throws IOException {
        byte[] result = childService.emailsForGroup(groupId).getBytes();
        response.addHeader("Content-Disposition", "attachment; filename=\"" + groupId + ".txt\"");
        response.addHeader("Content-Length", String.valueOf(result.length));
        response.getOutputStream().write(result);
    }

}
