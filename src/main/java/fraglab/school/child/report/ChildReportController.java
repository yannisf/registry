package fraglab.school.child.report;

import fraglab.school.child.Child;
import fraglab.school.child.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/child/report")
public class ChildReportController {

    @Autowired
    ChildService childService;

    @RequestMapping(value = "/pdf/{mode}", method = RequestMethod.GET)
    public String report(@PathVariable(value = "mode") String mode, Model model) throws Exception {
        List<Child> children = childService.fetchAll();
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