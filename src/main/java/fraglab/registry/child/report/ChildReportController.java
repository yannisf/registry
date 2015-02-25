package fraglab.registry.child.report;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.foundation.FoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/child/report")
public class ChildReportController {

    @Autowired
    FoundationService foundationService;

    @RequestMapping(value = "/pdf/{mode}", method = RequestMethod.GET)
    public String report(@PathVariable(value = "mode") String mode, @RequestParam(value = "class") String clazz,
                         Model model) throws Exception {
        List<Child> children = foundationService.fetchChildrenForGroup(clazz);
        model.addAttribute("children", children);
        if (mode.equals("first")) {
            return "childrenFirstNamesPdf";
        } else if (mode.equals("full")) {
            return "childrenFullNamesPdf";
        } else if (mode.equals("smallTags")) {
            return "childrenSmallTagNamesPdf";
        } else if (mode.equals("bigTags")) {
            return "childrenBigTagNamesPdf";
        } else {
            return null;
        }
    }
}
