package fraglab.registry.child.report;

import fraglab.registry.group.GroupDataTransfer;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ReportService {

    List<ReportChild> getReportChildrenForChildGroup(String childGroupId) throws NotFoundException;

    GroupDataTransfer getSchoolDataForChildGroup(String childGroupId);
}
