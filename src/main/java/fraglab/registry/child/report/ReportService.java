package fraglab.registry.child.report;

import fraglab.registry.school.SchoolData;
import fraglab.web.NotFoundException;

import java.util.List;

public interface ReportService {

    List<ReportChild> getReportChildrenForChildGroup(String childGroupId) throws NotFoundException;

    SchoolData getSchoolDataForChildGroup(String childGroupId);
}
