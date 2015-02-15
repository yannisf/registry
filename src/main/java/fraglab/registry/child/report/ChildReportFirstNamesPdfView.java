package fraglab.registry.child.report;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import fraglab.registry.child.Child;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("childrenFirstNamesPdf")
public class ChildReportFirstNamesPdfView extends AbstractChildReport {

    @Override
    String getDocumentTitle() {
        return "First names";
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> stringObjectMap, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        super.buildPdfDocument(stringObjectMap, document, pdfWriter, httpServletRequest, httpServletResponse);
        Font font = FontFactory.getFont("/fonts/DidactGothic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 64.0f, Font.NORMAL);
        @SuppressWarnings(value = "unchecked")
        List<Child> children = (List<Child>) stringObjectMap.get("children");
        for (Child child : children) {
            Paragraph paragraph = new Paragraph(child.getName(), font);
            document.add(paragraph);
        }
    }

}
