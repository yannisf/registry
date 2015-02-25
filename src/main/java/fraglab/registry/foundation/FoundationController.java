package fraglab.registry.foundation;

import fraglab.registry.child.Child;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.foundation.meta.GroupStatistics;
import fraglab.registry.foundation.meta.TreeElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
