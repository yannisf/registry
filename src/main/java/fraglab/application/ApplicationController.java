package fraglab.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Properties;

@RestController
@Scope(value = "singleton")
@RequestMapping("/application")
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    @Resource(name = "buildInfo")
    private Properties buildInfo;

    @RequestMapping(value = "/buildinfo", method = RequestMethod.GET)
    public Properties getBuildInfo() {
        return buildInfo;
    }

}
