package project.bigdata.semestriel.src;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.neo4j.core.Neo4jOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api")

@RestController
public class AccountController {

    private final AccountService accountService;
private final AccountRepository accountRepository;
    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }
    @GetMapping("/getAll")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Map<String, Object> accountMap) {
        String id = (String) accountMap.get("id");
        String username = (String) accountMap.get("username");
        String displayName = (String) accountMap.get("displayName");
        Map<String, Object> registeredOn = (Map<String, Object>) accountMap.get("registeredOn");
        return accountService.createAccount(id, username, displayName, registeredOn);
    }
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id ){
            accountService.deleteAccount(id);

    }

    @PostMapping("/accounts/{id}")
    public Account updateAccount(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        return accountService.updateAccount(id,payload);

    }
}
