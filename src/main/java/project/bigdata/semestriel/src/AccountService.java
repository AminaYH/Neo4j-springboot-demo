package project.bigdata.semestriel.src;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;
    private final ServerRepository serverRepository;
    private final ServerService serverService;

    public AccountService(AccountRepository accountRepository, ServerRepository serverRepository, ServerService serverService) {
        this.accountRepository = accountRepository;
        this.serverRepository = serverRepository;
        this.serverService = serverService;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Account createAccount(String id, String username, String displayName, Map<String, Object> registeredOn) {
        Account account = new Account(id, username, displayName);
        String serverUri = (String) registeredOn.get("uri");
        Optional<Server> server = serverService.findByUri(serverUri);
        if (server.isPresent()) {
            account.setRegisteredOn(server.get());
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Server not found");
        }
    }
    public void deleteAccount(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
        } else {
            throw new RuntimeException("Account not found");
        }
    }
    public Account updateAccount(String id,Map<String, Object> payload){

        Optional<Account> existingAccount = accountRepository.findById(id);
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            String newUsername = (String) payload.getOrDefault("username", account.getUsername());
            String newDisplayName = (String) payload.getOrDefault("displayName", account.getDisplayName());

            account.setUsername(newUsername);
            account.setDisplayName(newDisplayName);


            return accountRepository.save(account);
        }else {
           throw new RuntimeException("Account not found");
        }
    }
}
