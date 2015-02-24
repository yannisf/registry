package fraglab.registry.child.report;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import fraglab.registry.common.Telephone;
import fraglab.registry.child.Relationship;
import fraglab.web.NotFoundException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController()
@RequestMapping("/communication")
public class CommunicationReportController {

    private VelocityEngine velocityEngine;
    
    @Autowired
    private ReportService reportService;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    private void initialize() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "classpath");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public void table(@PathVariable(value = "id") String id,
                      @RequestParam(defaultValue = "a4", value = "format", required = false) String format,
                      HttpServletResponse response) 
            throws IOException, DocumentException, NotFoundException {
        String content = processTemplate(id, format);
        streamReport(response, content);
    }

    private String processTemplate(String id, String format) throws IOException, NotFoundException {
        Template template = velocityEngine.getTemplate("/templates/communication_report.vm", "UTF-8");
        VelocityContext context = createContext();
        context.put("format", format);
        context.put("schoolData", reportService.getSchoolDataForChildGroup(id));
        context.put("children", reportService.getReportChildrenForChildGroup(id));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    private void streamReport(HttpServletResponse response, String content) throws DocumentException, IOException {
        ITextRenderer renderer = getITextRendered();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }

    private VelocityContext createContext() throws IOException {
        VelocityContext context = new VelocityContext();
        context.put("css", getClasspathResource("/templates/communication_report.css"));
        context.put("school", getClasspathResource("/templates/school.png"));
        context.put("phoneTypeMap", getLocalizedTelephoneTypeMap());
        context.put("relationshipTypeMap", getLocalizedRelationshipTypeMap());
        return context;
    }

    private URL getClasspathResource(String resource) throws IOException {
        return resourceLoader.getResource("classpath:" + resource).getURL();
    }

    private ITextRenderer getITextRendered() throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("/fonts/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-Bold.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-Italic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-BoldItalic.ttf", BaseFont.IDENTITY_H, true);
        return renderer;
    }

    Map<Telephone.Type, String> getLocalizedTelephoneTypeMap() {
        Map<Telephone.Type, String> map = new HashMap<>();
        map.put(Telephone.Type.HOME, "ΣΠΙΤΙ");
        map.put(Telephone.Type.MOBILE, "KINΗΤΟ");
        map.put(Telephone.Type.WORK, "ΔΟΥΛΕΙΑ");
        map.put(Telephone.Type.OTHER, "ΑΛΛΟ");
        return map;
    }

    Map<Relationship.Type, String> getLocalizedRelationshipTypeMap() {
        Map<Relationship.Type, String> map = new HashMap<>();
        map.put(Relationship.Type.FATHER, "ΠΑΤΕΡΑΣ");
        map.put(Relationship.Type.MOTHER, "ΜΗΤΕΡΑ");
        map.put(Relationship.Type.GRANDFATHER, "ΠΑΠΠΟΥΣ");
        map.put(Relationship.Type.GRANDMOTHER, "ΓΙΑΓΙΑ");
        map.put(Relationship.Type.BROTHER, "ΑΔΕΛΦΟΣ");
        map.put(Relationship.Type.SISTER, "ΑΔΕΛΦΗ");
        map.put(Relationship.Type.UNCLE, "ΘΕΙΟΣ");
        map.put(Relationship.Type.AUNT, "ΘΕΙΑ");
        map.put(Relationship.Type.GODFATHER, "ΝΟΝΟΣ");
        map.put(Relationship.Type.GODMOTHER, "ΝΟΝΑ");
        map.put(Relationship.Type.OTHER, "ΑΛΛΟΣ");
        return map;
    }

}
