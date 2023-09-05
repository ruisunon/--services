package jphaugla.redisom.com.romsdocuments.repositories;

import com.redis.om.spring.annotations.Query;
import com.redis.om.spring.repository.RedisDocumentRepository;
import jphaugla.redisom.com.romsdocuments.domain.Company;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CompanyRepository extends RedisDocumentRepository<Company, String> {
    // find one by property
    Optional<Company> findOneByName(String name);

    // geospatial query
    Iterable<Company> findByLocationNear(Point point, Distance distance);
    // find by tag field, using JRediSearch "native" annotation
    @Query("@tags:{$tags}")
    Iterable<Company> findByTags(@Param("tags") Set<String> tags);

    @Query("@name:$prefix*")
    Iterable<Company> findByNameStartingWith(@Param("prefix") String prefix);

    @Query("@numberOfEmployees:[$noeGT $noeLT] ")
    Iterable<Company> findByNumberOfEmployeesBetween(@Param("noeGT") int noeGT, @Param("noeLT") int noeLT);
}
