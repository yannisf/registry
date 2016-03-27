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

}
