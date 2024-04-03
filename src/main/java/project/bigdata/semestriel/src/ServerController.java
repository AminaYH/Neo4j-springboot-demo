package project.bigdata.semestriel.src;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")

@RestController
public class ServerController {
    @Autowired
    private ServerService serverService;

    @GetMapping("/servers")
    public List<Server> getAllServers() {
        return serverService.getAllServers();
    }
    @GetMapping("/servers/{uri}")
    public ResponseEntity<Server> getServerByUri(@PathVariable String uri) {
        Optional<Server> serverOptional = serverService.findByUri(uri);

        return serverOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
