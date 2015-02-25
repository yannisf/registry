package fraglab.registry.foundation;

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

    @RequestMapping(value = "/info/{childGroupId}", method = RequestMethod.GET)
    public GroupDataTransfer fetchSchoolData(@PathVariable String childGroupId) {
        return foundationService.fetchSchoolData(childGroupId);
    }

    @RequestMapping(value = "/group/{childGroupId}/statistics", method = RequestMethod.GET)
    public GroupStatistics fetchChildGroupStatistics(@PathVariable String childGroupId) {
        return foundationService.fetchChildGroupStatistics(childGroupId);
    }

}
