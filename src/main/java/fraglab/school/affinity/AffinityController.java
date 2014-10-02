package fraglab.school.affinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/affinity")
public class AffinityController {

    private static final Logger LOG = LoggerFactory.getLogger(AffinityController.class);

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ChildGrownUpAffinity.Type[] getAllAffinities() {
        return ChildGrownUpAffinity.Type.values();
    }

}
