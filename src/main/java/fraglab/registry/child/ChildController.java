package fraglab.registry.child;

import com.lowagie.text.DocumentException;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/child")
public class ChildController extends BaseRestController {

    private static final int NAME_LENGTH_MOD_TRIGGER = 14;
    private static final String TEMPLATE_STYLE_MOD_PROPERTY = "mod";
    private static final String STYLE_MOD_VALUE = "-lg";
    private static final String CHILD_CARDS_TEMPLATE = "child_cards.ftl";
    private static final String CHILD_CARDS_TEMPLATE_CSS = "/templates/child_cards.css";

    @Autowired
    private
    ChildService childService;

    @Autowired
    private Configuration freemarkerConfiguration;

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
            throws IOException, DocumentException, NotFoundException, TemplateException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + id + ".pdf\"");
        String content = processTemplate(id);
        streamReport(response, content);
    }

    @PostMapping(value = "{id}/photo")
    public ResponseEntity<Void> uploadPhoto(@RequestParam("photo") MultipartFile file, @PathVariable("id") String id,
                                            UriComponentsBuilder uriComponentsBuilder)
            throws IOException, NoSuchAlgorithmException, NotFoundException {
        String photoId = childService.saveChildPhoto(id, file.getBytes());
        UriComponents uriComponents = uriComponentsBuilder.path("/child/photo/{photoId}").buildAndExpand(photoId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/photo/{photoId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable(value = "photoId") String photoId) {
        return childService.findChildPhoto(photoId)
                .map(childPhoto -> ResponseEntity.ok().body(childPhoto.getContent()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "{id}/photo", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable(value = "id") String id) {
        childService.deleteChildPhoto(id);
    }

    private String processTemplate(String id) throws IOException, NotFoundException, TemplateException {
        Child child = childService.find(id).orElseThrow(NotFoundException::new);
        Template template = freemarkerConfiguration.getTemplate(CHILD_CARDS_TEMPLATE, "UTF-8");
        Map<String, Object> context = createContext();
        context.put("child", child);
        modifyStyleForSize(child.getLastName(), context);
        StringWriter writer = new StringWriter();
        template.process(context, writer);
        System.out.println(writer.toString());
        return writer.toString();
    }

    private void modifyStyleForSize(String childLastName, Map<String, Object> context) {
        if (childLastName.length() >= NAME_LENGTH_MOD_TRIGGER) {
            context.put(TEMPLATE_STYLE_MOD_PROPERTY, STYLE_MOD_VALUE);
        }
    }

    private void streamReport(HttpServletResponse response, String content) throws DocumentException, IOException {
        iTextRenderer.setDocumentFromString(content);
        iTextRenderer.layout();
        iTextRenderer.createPDF(response.getOutputStream());
    }

    private Map<String, Object> createContext() throws IOException {
        Map<String, Object> context = new HashMap<>();
        context.put("css", getClasspathResource(CHILD_CARDS_TEMPLATE_CSS));
        return context;
    }

    private URL getClasspathResource(String resource) throws IOException {
        return resourceLoader.getResource("classpath:" + resource).getURL();
    }

}
