package project.bigdata.semestriel.src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    @Autowired
    private ServerRepository serverRepository;
    private static final Logger logger = LoggerFactory.getLogger(ServerService.class);


    @Autowired
    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Optional<Server> findByUri(String uri) {
        return serverRepository.findByUri(uri);
    }
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }
}
