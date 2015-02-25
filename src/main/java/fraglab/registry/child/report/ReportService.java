package fraglab.registry.child.report;

import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ReportService {

    List<ReportChild> getReportChildrenForChildGroup(String childGroupId) throws NotFoundException;

    GroupDataTransfer getSchoolDataForChildGroup(String childGroupId);
}
