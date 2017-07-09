package fraglab.registry.child.report;

import com.lowagie.text.DocumentException;
import fraglab.registry.common.Telephone;
import fraglab.registry.relationship.RelationshipType;
import fraglab.web.NotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/communication")
public class CommunicationReportController {

    @Resource(name = "reportRelationshipTypesGreekMap")
    Map<RelationshipType, String> reportRelationshipTypesGreekMap;

    @Resource(name = "reportTelephoneTypeGreekMap")
    Map<Telephone.Type, String> reportTelephoneTypeGreekMap;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    private ITextRenderer iTextRenderer;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public void table(@PathVariable(value = "id") String id,
                      @RequestParam(defaultValue = "a4", value = "format", required = false) String format,
                      HttpServletResponse response)
            throws IOException, DocumentException, NotFoundException, TemplateException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + id + ".pdf\"");
        String content = processTemplate(id, format);
        streamReport(response, content);
    }

    private String processTemplate(String id, String format) throws IOException, NotFoundException, TemplateException {
        Template template = freemarkerConfiguration.getTemplate("communication_report.ftl");
        Map<String, Object> context = createContext();
        context.put("format", format);
        context.put("schoolData", reportService.getSchoolDataForChildGroup(id));
        context.put("children", reportService.getReportChildrenForChildGroup(id));
        StringWriter writer = new StringWriter();
        template.process(context, writer);
        return writer.toString();
    }

    private void streamReport(HttpServletResponse response, String content) throws DocumentException, IOException {
        iTextRenderer.setDocumentFromString(content);
        iTextRenderer.layout();
        iTextRenderer.createPDF(response.getOutputStream());
    }

    private Map<String, Object> createContext() throws IOException {
        Map<String, Object> context = new HashMap<>();
        context.put("css", getClasspathResource("/templates/communication_report.css"));

        //TODO: make freemarker understand enums
        context.put("relationshipTypeMap", reportRelationshipTypesGreekMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue)));

        //TODO: make freemarker understand enums
        context.put("phoneTypeMap", reportTelephoneTypeGreekMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue)));

        return context;
    }

    private URL getClasspathResource(String resource) throws IOException {
        return resourceLoader.getResource("classpath:" + resource).getURL();
    }

}
