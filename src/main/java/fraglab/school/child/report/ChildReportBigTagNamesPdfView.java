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

@Component("childrenBigTagNamesPdf")
public class ChildReportBigTagNamesPdfView extends AbstractChildReport {

    public static final float BASE_FONT_SIZE = 28.0f;
    public static final Font BASE_FONT = FontFactory.getFont("/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED, BASE_FONT_SIZE, Font.NORMAL);
    public static final float SMALLER_FONT_SIZE = 24.0f;
    public static final Font SMALLER_FONT = FontFactory.getFont("/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H,
            BaseFont.EMBEDDED, SMALLER_FONT_SIZE, Font.NORMAL);

    @Override
    String getDocumentTitle() {
        return "Big tag names";
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> stringObjectMap, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        super.buildPdfDocument(stringObjectMap, document, pdfWriter, httpServletRequest, httpServletResponse);
        @SuppressWarnings(value = "unchecked")
        List<Child> children = (List<Child>) stringObjectMap.get("children");
        Table table = getTable();
        for (Child child : children) {
            Font actualFont = evaluateActualFont(child.getName(), child.getLastName());
            Phrase phrase = new Phrase(child.getName() + "\n" + child.getLastName(), actualFont);
            table.addCell(phrase);
        }
        document.add(table);
    }

    private Font evaluateActualFont(String... names) {
        for (String name : names) {
            if (name.length() > 10) {
                return SMALLER_FONT;
            }
        }
        return BASE_FONT;
    }

    private Table getTable() throws BadElementException {
        Table table = new Table(2);
        table.setPadding(8.0f);
        table.setBorder(0);
        table.setTableFitsPage(true);
        return table;
    }

}
