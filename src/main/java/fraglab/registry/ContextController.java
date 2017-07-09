package fraglab.registry;

import fraglab.application.WebappSecureMode;
import fraglab.web.BaseRestController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/context")
public class ContextController extends BaseRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ContextController.class);

    private static final String NAME_KEY = "name";
    private static final String AUTHORITIES_KEY = "authorities";

    public static final String GIT_REV = "Git-Rev";
    public static final String BUILD_TIME = "Build-Time";


    @Autowired
    private ManifestHelper manifestHelper;

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
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put(GIT_REV, manifestHelper.getValue(GIT_REV));
        infoMap.put(BUILD_TIME, manifestHelper.getValue(BUILD_TIME));
        infoMap.put("spring-profile", System.getProperty("spring.profiles.active"));
        infoMap.put("secure", System.getProperty("secure"));

        return infoMap;
    }

}
