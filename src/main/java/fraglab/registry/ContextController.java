package fraglab.registry;

import fraglab.web.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/context")
public class ContextController extends BaseRestController {

    /**
     * Provides user information for logged in users from the security provider
     * @return user details
     */
    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>();
        map.put("name", auth.getName());
        map.put("authorities", auth.getAuthorities());
        map.put("principal", auth.getPrincipal());
        map.put("details", auth.getDetails());
        return map;
    }

}
