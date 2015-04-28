package fraglab.registry;

import fraglab.web.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/context")
public class ContextController extends BaseRestController {

    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getContextPath(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("contextPath", request.getContextPath());
        return map;
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getAuthentication(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        Map<String, Object> map = new HashMap<>();
        String authName = auth.getName();
        map.put("name", auth.getName());
        return map;
    }

}
