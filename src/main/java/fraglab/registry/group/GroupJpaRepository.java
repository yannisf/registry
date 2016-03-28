package fraglab.registry.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJpaRepository extends JpaRepository<Group, String> {

    @Modifying
    @Query("update Group g set g.members=(select count(c) from Child c where c.group=:group) where g=:group")
    int queryForUpdateMemberCount(@Param("group") Group group);

    @Query("select new fraglab.registry.group.GroupDataTransfer("
            + "g.id, s.name, cr.name, g.name, g.members) "
            + "from Group g join g.department cr join g.department.school s "
            + "where g.id=:groupId order by g.name")
    GroupDataTransfer queryByGroupForMetadata(@Param("groupId") String groupId);

    @Query(nativeQuery = true, value = "select "
            + "BOYS.BOYS_NUMBER boysNum, "
            + "GIRLS.GIRLS_NUMBER girlsNum, "
            + "PRESCHOOL_LEVEL_A.PRESCHOOL_LEVEL_A_NUMBER levelaNum, "
            + "PRESCHOOL_LEVEL_B.PRESCHOOL_LEVEL_B_NUMBER levelbNum from "
            + "(select count(*) AS BOYS_NUMBER from Person p "
            + "where p.group_id = :groupId and p.gender = 'MALE') BOYS, "
            + "(select count(*) AS GIRLS_NUMBER from Person p "
            + "where p.group_id = :groupId and p.gender = 'FEMALE') GIRLS, "
            + "(select count(*) AS PRESCHOOL_LEVEL_A_NUMBER from Person p "
            + "where p.group_id = :groupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_A') PRESCHOOL_LEVEL_A, "
            + "(select count(*)AS PRESCHOOL_LEVEL_B_NUMBER from Person p "
            + "where p.group_id = :groupId and p.PRESCHOOL_LEVEL = 'PRE_SCHOOL_LEVEL_B') PRESCHOOL_LEVEL_B ")
    Object queryByGroupForStatistics(@Param("groupId") String groupId);

}