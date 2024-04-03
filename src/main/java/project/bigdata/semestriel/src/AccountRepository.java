package project.bigdata.semestriel.src;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends Neo4jRepository<Account, String> {
    @Query("MATCH (a:Account)-[r:REGISTERED_ON]->(s:Server {uri: $serverUri}) DELETE r")
    void deleteRegisteredOnRelationship(@Param("serverUri") String serverUri);

}