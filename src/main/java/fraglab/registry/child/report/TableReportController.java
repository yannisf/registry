package fraglab.registry.child.report;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

@RestController()
@RequestMapping("/table")
public class TableReportController {

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(method = RequestMethod.GET, produces = "application/pdf")
    public void table(HttpServletResponse response) throws IOException, DocumentException {
        System.out.println("Hdsddd");
        Resource resource = resourceLoader.getResource("classpath:/templates/template.html");
        StringWriter writer = new StringWriter();
        IOUtils.copy(resource.getInputStream(), writer, "UTF-8");
        String content = writer.toString();
        System.out.println(content);
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("/fonts/OpenSans-Regular.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-Bold.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-Italic.ttf", BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont("/fonts/OpenSans-BoldItalic.ttf", BaseFont.IDENTITY_H, true);
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }

}
