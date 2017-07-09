package fraglab.registry.overview;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.department.Department;
import fraglab.registry.group.Group;
import fraglab.registry.group.GroupStatistics;
import fraglab.registry.school.School;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import freemarker.template.TemplateException;
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

    @GetMapping(value = "security")
    public Authentication credentials() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PutMapping(value = "/school")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveSchool(@RequestBody School school) {
        overviewService.saveSchool(school);
    }

    @GetMapping(value = "/school")
    public List<School> findSchools() {
        return overviewService.findSchools();
    }

    @DeleteMapping(value = "/school/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteSchool(@PathVariable String id) {
        overviewService.deleteSchool(id);
    }

    @GetMapping(value = "/department")
    public List<Department> findDepartmentsForSchool(@RequestParam(value = "schoolId", required = true) String schoolId) {
        return overviewService.findDepartmentsForSchool(schoolId);
    }

    @PutMapping(value = "/department")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveDepartmentForSchool(@RequestParam(value = "schoolId") String schoolId,
                                        @RequestBody Department department) {
        overviewService.saveDepartmentForSchool(schoolId, department);
    }

    @DeleteMapping(value = "/department/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDepartment(@PathVariable String id) {
        overviewService.deleteDepartment(id);
    }

    @GetMapping(value = "/group")
    public List<Group> findGroupsForDepartment(@RequestParam(value = "departmentId", required = true) String departmentId) {
        return overviewService.findGroupsForDepartment(departmentId);
    }

    @PutMapping(value = "/group")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveGroup(@RequestBody Group group, @RequestParam(value = "departmentId") String departmentId) {
        overviewService.saveGroupForDepartment(group, departmentId);
    }

    @DeleteMapping(value = "/group/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteGroup(@PathVariable String id) {
        overviewService.deleteGroup(id);
    }

    @GetMapping(value = "/group/{id}/child")
    public List<Child> findChildrenForGroup(@PathVariable String id) {
        return overviewService.findChildrenForGroup(id);
    }

    @GetMapping(value = "/group/{id}/statistics")
    public GroupStatistics findChildGroupStatistics(@PathVariable String id) {
        return overviewService.findChildGroupStatistics(id);
    }

    @GetMapping(value = "/group/{id}/info")
    public Map<String, Object> findGroupInfo(@PathVariable String id) throws NotFoundException {
        return overviewService.findGroupInfo(id);
    }

    @GetMapping(value = "/group/{groupId}/emails", produces = "text/plain; charset=UTF-8")
    public void findGroupEmails(@PathVariable String groupId, HttpServletResponse response) throws IOException, TemplateException {
        byte[] result = childService.emailsForGroup(groupId).getBytes();
        response.addHeader("Content-Disposition", "attachment; filename=\"" + groupId + ".txt\"");
        response.addHeader("Content-Length", String.valueOf(result.length));
        response.getOutputStream().write(result);
    }

}
