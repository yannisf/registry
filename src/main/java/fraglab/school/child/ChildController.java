package fraglab.school.child;

import fraglab.NotFoundException;
import fraglab.school.ControllerErrorWrapper;
import fraglab.school.affinity.AffinityDto;
import fraglab.school.affinity.AffinityMetadata;
import fraglab.school.affinity.ChildGrownUpAffinity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController {

    private static final Logger LOG = LoggerFactory.getLogger(ChildController.class);

    @Autowired
    ChildService childService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Child child) {
        childService.create(child);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Child child) throws NotFoundException {
        childService.update(id, child);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        childService.delete(id);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable Long id) throws NotFoundException {
        return childService.fetch(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Child> fetchAll() {
        return childService.fetchAll();
    }

    @RequestMapping(value = "/{childId}/grownup/{grownupId}", method = RequestMethod.GET)
    public ChildGrownUpAffinity fetchAffinity(@PathVariable() Long childId, @PathVariable() Long grownupId) throws NotFoundException {
        return childService.fetchAffinity(childId, grownupId);
    }

    @RequestMapping(value = "/{childId}/grownup", method = RequestMethod.GET)
    public List<AffinityDto> fetchAffinity(@PathVariable() Long childId) throws NotFoundException {
        return childService.fetchAffinities(childId);
    }

    @RequestMapping(value = "/{childId}/grownup/{grownupId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createAffinity(@PathVariable() Long childId, @PathVariable() Long grownupId,
                               @RequestBody AffinityMetadata affinityMetadata) throws NotFoundException {
        childService.createAffinity(childId, grownupId, affinityMetadata);
    }

    @RequestMapping(value = "/{childId}/grownup/{grownupId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAffinity(@PathVariable() Long childId, @PathVariable() Long grownupId) throws NotFoundException {
        childService.deleteAffinity(childId, grownupId);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    ControllerErrorWrapper handleNotFoundException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Resource not found", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    ControllerErrorWrapper handleException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Bad request", e.getMessage());
    }


}
