package fraglab.registry.child;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

@RestController
@RequestMapping("/child")
public class ChildController extends BaseRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ChildController.class);
    private static final int NAME_LENGTH_MOD_TRIGGER = 14;
    private static final String TEMPLATE_STYLE_MOD_PROPERTY = "mod";
    private static final String STYLE_MOD_VALUE = "-lg";
    private static final String CHILD_CARDS_TEMPLATE = "/templates/child_cards.vm";
    private static final String CHILD_CARDS_TEMPLATE_CSS = "/templates/child_cards.css";
    private static final String FONTS_DIDACT_GOTHIC_PATH = "/fonts/DidactGothic.ttf";

    //TODO: Initialize this from Spring and inject it wherever needed
    private VelocityEngine velocityEngine;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    ChildService childService;

    @PostConstruct
    private void initialize() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "classpath");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestBody Child child, @RequestParam("addressId") String addressId, 
                               @RequestParam("groupId") String groupId) 
            throws NotIdentifiedException, NotFoundException {
        childService.createOrUpdate(child, addressId, groupId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable String id) throws NotFoundException {
        return childService.fetch(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        childService.delete(id);
    }
    
    @RequestMapping(value = "/{id}/cards", method = RequestMethod.GET)
    public void printCards(@PathVariable(value = "id") String id, HttpServletResponse response)
            throws IOException, DocumentException, NotFoundException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + id + ".pdf\"");
        String content = processTemplate(id);
        streamReport(response, content);
    }


    private String processTemplate(String id) throws IOException, NotFoundException {
        Child child = childService.fetch(id);
        Template template = velocityEngine.getTemplate(CHILD_CARDS_TEMPLATE, "UTF-8");
        VelocityContext context = createContext();
        context.put("child", child);
        modifyStyleForSize(child.getLastName(), context);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    private void modifyStyleForSize(String childsLastName, VelocityContext context) {
        if (childsLastName.length() >= NAME_LENGTH_MOD_TRIGGER) {
            context.put(TEMPLATE_STYLE_MOD_PROPERTY, STYLE_MOD_VALUE);
        }
    }

    private void streamReport(HttpServletResponse response, String content) throws DocumentException, IOException {
        ITextRenderer renderer = getITextRendered();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }

    private VelocityContext createContext() throws IOException {
        VelocityContext context = new VelocityContext();
        context.put("css", getClasspathResource(CHILD_CARDS_TEMPLATE_CSS));
        return context;
    }

    private URL getClasspathResource(String resource) throws IOException {
        return resourceLoader.getResource("classpath:" + resource).getURL();
    }

    private ITextRenderer getITextRendered() throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(FONTS_DIDACT_GOTHIC_PATH, BaseFont.IDENTITY_H, true);
        return renderer;
    }

}
