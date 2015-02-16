package fraglab.registry.child.report;

import fraglab.web.NotFoundException;

import java.util.List;

public interface ReportService {

    List<Child> getReportChildrenForChildGroup(String childGroupId) throws NotFoundException;

}
