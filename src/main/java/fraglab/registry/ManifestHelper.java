package fraglab.registry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Parses the web application MANIFEST.MF and makes its keys available through an API.
 */
@Component
public class ManifestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ContextController.class);

    private static final String MANIFEST = "/META-INF/MANIFEST.MF";

    public static final String N_A = "N/A";

    @Autowired
    private ServletContext servletContext;

    private Map<String, String> manifestEntries;

    @PostConstruct
    void initialize() {
        LOG.debug("Consuming [{}]", MANIFEST);
        try (InputStream manifestStream = this.servletContext.getResourceAsStream(MANIFEST)) {
            manifestEntries = IOUtils.readLines(manifestStream).stream()
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toMap(
                            l -> StringUtils.strip(StringUtils.split(l, ":")[0]),
                            l -> StringUtils.strip(StringUtils.split(l, ":")[1])));
            LOG.debug("Found keys: [{}]", manifestEntries.entrySet().stream()
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", ")));
        } catch (Exception e) {
            manifestEntries = Collections.EMPTY_MAP;
            LOG.warn("Web application MANIFEST.MF could not be processed");
        }
    }

    public String getValue(String key) {
        String value = manifestEntries.get(key);
        return value == null ? N_A : value;
    }

}
