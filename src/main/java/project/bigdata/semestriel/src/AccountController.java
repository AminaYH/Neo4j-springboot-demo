package project.bigdata.semestriel.src;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
    @DeleteMapping("/{username}")
    public void deleteAcount(Account account){

    }

}
