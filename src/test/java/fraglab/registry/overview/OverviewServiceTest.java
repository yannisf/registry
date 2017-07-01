package fraglab.registry.overview;

import fraglab.registry.child.ChildJpaRepository;
import fraglab.registry.department.DepartmentJpaRepository;
import fraglab.registry.group.GroupJpaRepository;
import fraglab.registry.group.GroupStatistics;
import fraglab.registry.school.SchoolJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OverviewServiceTest {

    @Mock
    private ChildJpaRepository childJpaRepository;

    @Mock
    private SchoolJpaRepository schoolJpaRepository;

    @Mock
    private DepartmentJpaRepository departmentJpaRepository;

    @Mock
    private GroupJpaRepository groupJpaRepository;

    @InjectMocks
    private OverviewServiceImpl overviewService;

    @Test
    public void test() {
        String validGroupId = "validGroupId";
        when(groupJpaRepository.queryByGroupForStatistics(eq(validGroupId)))
                .then(invocation -> new Object[]{
                        new BigInteger("1"),
                        new BigInteger("2"),
                        new BigInteger("3"),
                        new BigInteger("4")
                });

        GroupStatistics groupStatistics = overviewService.findChildGroupStatistics(validGroupId);
        verify(groupJpaRepository).queryByGroupForStatistics(eq(validGroupId));
        assertThat(groupStatistics.getGroupId(), equalTo(validGroupId));
        assertThat(groupStatistics.getBoysNum(), equalTo(1));
        assertThat(groupStatistics.getGirlsNum(), equalTo(2));
        assertThat(groupStatistics.getLevelaNum(), equalTo(3));
        assertThat(groupStatistics.getLevelbNum(), equalTo(4));
    }

}
