package fraglab.registry.child;

import com.lowagie.text.DocumentException;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/child")
public class ChildController extends BaseRestController {

    private static final int NAME_LENGTH_MOD_TRIGGER = 14;
    private static final String TEMPLATE_STYLE_MOD_PROPERTY = "mod";
    private static final String STYLE_MOD_VALUE = "-lg";
    private static final String CHILD_CARDS_TEMPLATE = "/templates/child_cards.vm";
    private static final String CHILD_CARDS_TEMPLATE_CSS = "/templates/child_cards.css";

    @Autowired
    private
    ChildService childService;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private ITextRenderer iTextRenderer;

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Child find(@PathVariable String id) throws NotFoundException {
        return childService.find(id).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody Child child, @RequestParam("addressId") String addressId,
                     @RequestParam("groupId") String groupId) {
        childService.save(child, addressId, groupId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        childService.delete(id);
    }

    @RequestMapping(value = "/{id}/cards", method = RequestMethod.GET)
    public void printCards(@PathVariable(value = "id") String id, HttpServletResponse response)
            throws IOException, DocumentException, NotFoundException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + id + ".pdf\"");
        String content = processTemplate(id);
        streamReport(response, content);
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadPhoto(@RequestParam("photo") MultipartFile file, @PathVariable("id") String id)
            throws IOException, NoSuchAlgorithmException, NotFoundException {
        childService.saveChildPhoto(id, file.getBytes());
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable(value = "id") String id) {
        Optional<ChildPhoto> photo = childService.findChildPhoto(id);

        return photo
                .map(childPhoto -> ResponseEntity.ok().body(childPhoto.getContent()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.HEAD, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Void> downloadPhotoCheck(@PathVariable(value = "id") String id) {
        Optional<ChildPhoto> photo = childService.findChildPhoto(id);
        if (photo.isPresent()) {
            return ResponseEntity.ok().body(null);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable(value = "id") String id) {
        childService.deleteChildPhoto(id);
    }

    private String processTemplate(String id) throws IOException, NotFoundException {
        Child child = childService.find(id).orElseThrow(NotFoundException::new);
        Template template = velocityEngine.getTemplate(CHILD_CARDS_TEMPLATE, "UTF-8");
        VelocityContext context = createContext();
        context.put("child", child);
        modifyStyleForSize(child.getLastName(), context);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    private void modifyStyleForSize(String childLastName, VelocityContext context) {
        if (childLastName.length() >= NAME_LENGTH_MOD_TRIGGER) {
            context.put(TEMPLATE_STYLE_MOD_PROPERTY, STYLE_MOD_VALUE);
        }
    }

    private void streamReport(HttpServletResponse response, String content) throws DocumentException, IOException {
        iTextRenderer.setDocumentFromString(content);
        iTextRenderer.layout();
        iTextRenderer.createPDF(response.getOutputStream());
    }

    private VelocityContext createContext() throws IOException {
        VelocityContext context = new VelocityContext();
        context.put("css", getClasspathResource(CHILD_CARDS_TEMPLATE_CSS));
        return context;
    }

    private URL getClasspathResource(String resource) throws IOException {
        return resourceLoader.getResource("classpath:" + resource).getURL();
    }

}
