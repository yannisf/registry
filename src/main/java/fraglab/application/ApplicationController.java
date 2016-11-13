package fraglab.application;

import com.jcabi.manifests.Manifests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Genric controller for a wide range of application information
 */
@RestController
@Scope(value = "singleton")
@RequestMapping("/application")
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    /**
     * Provides information on the application.
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, String> getBuildInfo() {
        LOG.debug("Opening manifest to retrieve custom application entries...");
        String gitHash = Manifests.read(ApplicationManifest.GIT_HASH.getEntry());
        String springProfile = Manifests.read(ApplicationManifest.SPRING_PROFILE.getEntry());

        Map<String, String> infoMap = new HashMap<>();
        infoMap.put(ApplicationManifest.GIT_HASH.getEntry(), gitHash);
        infoMap.put(ApplicationManifest.SPRING_PROFILE.getEntry(), springProfile);

        return infoMap;
    }

}
