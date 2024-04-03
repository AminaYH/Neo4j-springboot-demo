package project.bigdata.semestriel.src;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends Neo4jRepository<Server, String> {
}
