package fraglab.registry;

import fraglab.application.WebappSecureMode;
import fraglab.web.BaseRestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/context")
public class ContextController extends BaseRestController {

    private static final String NAME_KEY = "name";
    private static final String AUTHORITIES_KEY = "authorities";

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

}
