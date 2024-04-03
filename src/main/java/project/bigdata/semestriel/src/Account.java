package project.bigdata.semestriel.src;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Getter
@Setter
@Node("Account")
public class Account {

    @Id
    private final String id; // (1)
    @Property("display_name")
    private  String displayName;
    private String username;// (2)
    @Relationship(type = "REGISTERED_ON", direction = Relationship.Direction.OUTGOING)
    private Server registeredOn;

    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    private List<Account> follows;

    public Account(String id, String username, String displayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }



}
