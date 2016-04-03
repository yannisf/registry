package fraglab.registry.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseEntityJpaRepository extends JpaRepository<BaseEntity, String> {

    @Query("select distinct p.firstName from Person p where lower(p.firstName) like :startsWith order by p.firstName")
    List<String> queryByFirstName(@Param("startsWith") String startsWith, Pageable pageable);

    @Query("select distinct p.lastName from Person p where lower(p.lastName) like :startsWith order by p.lastName")
    List<String> queryByLastName(@Param("startsWith") String startsWith, Pageable pageable);

    @Query("select distinct g.profession from Guardian g where lower(g.profession) like :startsWith order by g.profession")
    List<String> queryByProfession(@Param("startsWith") String startsWith, Pageable pageable);

    @Query("select distinct p.nationality from Person p where lower(p.nationality) like :startsWith order by p.nationality")
    List<String> queryByNationality(@Param("startsWith") String startsWith, Pageable pageable);

    @Query("select distinct a.streetName from Address a where lower(a.streetName) like :startsWith order by a.streetName")
    List<String> queryByStreetName(@Param("startsWith") String startsWith, Pageable pageable);

    @Query("select distinct a.neighbourhood from Address a where lower(a.neighbourhood) like :startsWith order by a.neighbourhood")
    List<String> queryByNeighbourhood(@Param("startsWith") String startsWith, Pageable pageable);

    @Query("select distinct a.city from Address a where lower(a.city) like :startsWith order by a.city")
    List<String> queryByCity(@Param("startsWith") String startsWith, Pageable pageable);

}
