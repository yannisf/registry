package fraglab.school.child.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import fraglab.school.child.Child;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("childrenSmallTagNamesPdf")
public class ChildReportSmallTagNamesPdfView extends AbstractChildReport {

    @Override
    protected Document newDocument() {
        return new Document(PageSize.A4, 100.0f, 80.0f, 80.0f, 80.0f);

    }

    @Override
    protected void buildPdfDocument(Map<String, Object> stringObjectMap, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        super.buildPdfDocument(stringObjectMap, document, pdfWriter, httpServletRequest, httpServletResponse);
        Font font = FontFactory.getFont("/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16.0f, Font.NORMAL);
        @SuppressWarnings(value = "unchecked")
        List<Child> children = (List<Child>) stringObjectMap.get("children");
        Table table = getTable();
        for (Child child : children) {
            Phrase phrase = new Phrase(child.getName() + "\n" + child.getLastName(), font);
            table.addCell(phrase);
        }
        document.add(table);
    }

    private Table getTable() throws BadElementException {
        Table table = new Table(2);
        table.setPadding(8.0f);
        table.setBorder(0);
        table.setTableFitsPage(true);
        return table;
    }

    @Override
    String getDocumentTitle() {
        return "Small tag names";
    }
}
