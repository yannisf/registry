package fraglab.school.child.report;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

abstract class AbstractChildReport extends AbstractPdfView {

    abstract String getDocumentTitle();

    @Override
    protected Document newDocument() {
        Document document = super.newDocument();
        document.setPageSize(PageSize.A4);
        return document;
    }

    @Override
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
        String user = "unknown";
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            user = principal.getName();
        }
        document.addAuthor(user);
        document.addCreationDate();
        document.addCreator("School Application");
        document.addSubject("School  printouts");
        document.addTitle(getDocumentTitle());
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> stringObjectMap, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + getFilename() + "\"");
    }

    String getFilename() {
        return getDocumentTitle() + ".pdf";
    }

}
