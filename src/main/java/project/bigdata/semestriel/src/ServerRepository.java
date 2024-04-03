package project.bigdata.semestriel.src;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends Neo4jRepository<Server, String> {
    @Query("MATCH (s:Server {uri: $uri}) RETURN s")
    Optional<Server> findByUri(@Param("uri") String uri);

}
