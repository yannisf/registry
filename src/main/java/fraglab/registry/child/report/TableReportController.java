package fraglab.registry.child.report;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController()
@RequestMapping("/table")
public class TableReportController {

    private VelocityEngine velocityEngine;

    @PostConstruct
    private void initialize() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "classpath");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
    public void table(HttpServletResponse response) throws IOException, DocumentException {
        Template template = velocityEngine.getTemplate("/templates/template.html", "UTF-8");
        VelocityContext context = new VelocityContext();
        context.put("children", createChildList());
        context.put("school", getClass().getResource("/templates/school.png"));
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String content = writer.toString();
        ITextRenderer renderer = getITextRendered();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }

    private ITextRenderer getITextRendered() throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("/fonts/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-Bold.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-Italic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-BoldItalic.ttf", BaseFont.IDENTITY_H, true);
        return renderer;
    }

    List<Child> createChildList() {
        List<Child> children = new ArrayList<>();

        Child child1 = new Child("Manolis Mitsias");
        Guardian guardian1 = new Guardian("Andreas Mitsias", "pateras", false);
        guardian1.addTelephone("2104545879", "S");
        guardian1.addTelephone("6904545879", "K");
        Guardian guardian2 = new Guardian("Maria Mitsias", "mana", true);
        guardian2.addTelephone("2104545879", "S");
        guardian2.addTelephone("6984545879", "K");
        child1.addGuardian(guardian1);
        child1.addGuardian(guardian2);
        child1.setRemarks("Some remarks");
        children.add(child1);
        children.add(child1);
        children.add(child1);
        children.add(child1);
        children.add(child1);
        children.add(child1);
        children.add(child1);
        children.add(child1);
        return children;
    }

}
