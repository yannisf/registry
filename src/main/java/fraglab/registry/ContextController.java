package fraglab.registry;

import com.jcabi.manifests.Manifests;
import fraglab.application.WebappSecureMode;
import fraglab.web.BaseRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/context")
public class ContextController extends BaseRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ContextController.class);

    private static final String NAME_KEY = "name";
    private static final String AUTHORITIES_KEY = "authorities";
    public static final String GIT_REV = "Git-Rev";
    public static final String BUILD_TIME = "Build-Time";

    /**
     * Provides user information for logged in users from the security provider.
     *
     * @return user details
     */
    @GetMapping(value = "/authentication")
    public Map<String, Object> getAuthentication() {
        Map<String, Object> map = new HashMap<>();

        if (WebappSecureMode.isSecure()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            map.put(NAME_KEY, auth.getName());
            map.put(AUTHORITIES_KEY, auth.getAuthorities());
        } else {
            map.put(NAME_KEY, "user");
            map.put(AUTHORITIES_KEY, "[{\"authority\":\"USER\"}]");
        }

        return map;
    }

    @GetMapping(value = "/info")
    public Map<String, String> getBuildInfo() {
        String gitRevision = Manifests.read(GIT_REV);
        String buildTimeStamp = Manifests.read(BUILD_TIME);
        String springProfile = System.getProperty("spring.profiles.active");
        String secureMode = System.getProperty("secure");

        Map<String, String> infoMap = new HashMap<>();
        infoMap.put(GIT_REV, gitRevision);
        infoMap.put(BUILD_TIME, buildTimeStamp);
        infoMap.put("spring-profile", springProfile);
        infoMap.put("secure", secureMode);

        return infoMap;
    }

}
