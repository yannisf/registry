package fraglab.school.child.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import fraglab.school.child.Child;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("childrenBigTagNamesPdf")
public class ChildReportBigTagNamesPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> stringObjectMap, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        Font font = FontFactory.getFont("/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 28.0f, Font.NORMAL);
        @SuppressWarnings(value = "unchecked")
        List<Child> children = (List<Child>) stringObjectMap.get("children");
        Table table = new Table(2);
        table.setPadding(8.0f);
        table.setBorder(0);
        for (Child child : children) {
            Phrase phrase = new Phrase(child.getName() + "\n" + child.getLastName(), font);
            table.addCell(phrase);
        }
        table.setLocked(true);
        document.add(table);
    }

}
