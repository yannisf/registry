package fraglab.application;

import com.jcabi.manifests.Manifests;
import com.jcabi.manifests.ServletMfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@Scope(value = "singleton")
@RequestMapping("/application")
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, String> getBuildInfo(HttpServletRequest request) {
        String gitHash = Manifests.read("git-hash");
        String springProfile = Manifests.read("spring-profile");
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("git-hash", gitHash);
        infoMap.put("spring-profile", springProfile);

        return infoMap;
    }

}
