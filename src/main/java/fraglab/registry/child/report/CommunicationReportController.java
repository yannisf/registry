package fraglab.registry.child.report;

import com.lowagie.text.DocumentException;
import fraglab.registry.common.Telephone;
import fraglab.registry.relationship.RelationshipType;
import fraglab.web.NotFoundException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

@RestController()
@RequestMapping("/communication")
public class CommunicationReportController {

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private ITextRenderer iTextRenderer;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Resource(name = "reportRelationshipTypesGreekMap")
    Map<RelationshipType, String> reportRelationshipTypesGreekMap;

    @Resource(name = "reportTelephoneTypeGreekMap")
    Map<Telephone.Type, String> reportTelephoneTypeGreekMap;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public void table(@PathVariable(value = "id") String id,
                      @RequestParam(defaultValue = "a4", value = "format", required = false) String format,
                      HttpServletResponse response) 
            throws IOException, DocumentException, NotFoundException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + id + ".pdf\"");
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
        iTextRenderer.setDocumentFromString(content);
        iTextRenderer.layout();
        iTextRenderer.createPDF(response.getOutputStream());
    }

    private VelocityContext createContext() throws IOException {
        VelocityContext context = new VelocityContext();
        context.put("css", getClasspathResource("/templates/communication_report.css"));
        context.put("school", getClasspathResource("/templates/school.png"));
        context.put("relationshipTypeMap", reportRelationshipTypesGreekMap);
        context.put("phoneTypeMap", reportTelephoneTypeGreekMap);
        return context;
    }

    private URL getClasspathResource(String resource) throws IOException {
        return resourceLoader.getResource("classpath:" + resource).getURL();
    }

}
