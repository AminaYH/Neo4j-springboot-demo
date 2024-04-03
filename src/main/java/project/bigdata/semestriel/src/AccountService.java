package project.bigdata.semestriel.src;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Account createAccount(String id, String username, String displayName, Map<String, Object> payload) {
        Account account = new Account(id, username, displayName);
        Map<String, Object> newRegisteredOnPayload = (Map<String, Object>) payload.getOrDefault("registeredOn", account.getRegisteredOn());

        String serverUri = (String) newRegisteredOnPayload.getOrDefault("uri", "");
        Optional<Server> server = serverService.findByUri(serverUri);

        if (server.isPresent()) {
            account.setRegisteredOn(server.get());

        } else {
            throw new RuntimeException("Server not found");
        }
        List<Map<String, Object>> newFollowsPayload = (List<Map<String, Object>>) payload.getOrDefault("follows", account.getFollows());

        List<Account> newFollows = new ArrayList<>();
        for (Map<String, Object> followPayload : newFollowsPayload) {
            String followId = (String) followPayload.get("id");
            Optional<Account> existingFollow = accountRepository.findById(followId);
            Account follow = existingFollow.orElse(null);
            if (follow == null) {
                follow = new Account(followId, followPayload.get("username").toString(), followPayload.get("displayName").toString());
            }
            newFollows.add(follow);
        }
        account.setFollows(newFollows);
        return accountRepository.save(account);

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
            Map<String, Object> registeredOnPayload = (Map<String, Object>) payload.getOrDefault("registeredOn", null);
            if (registeredOnPayload != null) {
                String serverUri = (String) registeredOnPayload.getOrDefault("uri", "");
                Optional<Server> existingServerOptional = serverRepository.findByUri(serverUri);
                if (existingServerOptional.isPresent()) {

                    Server existingServer = existingServerOptional.get();
                    if (account.getRegisteredOn() != null) {
                        Server registeredOnServer = account.getRegisteredOn();
                        accountRepository.deleteRegisteredOnRelationship(registeredOnServer.getUri());
                    }
                    if (!existingServer.equals(account.getRegisteredOn())) {
                        account.setRegisteredOn(existingServer);

                    }
                } else {
                    throw new RuntimeException("Server not found");
                }
            }



            List<Map<String, Object>> newFollowsPayload = (List<Map<String, Object>>) payload.getOrDefault("follows", account.getFollows());
            for (Map<String, Object> followPayload : newFollowsPayload) {
            }
            return accountRepository.save(account);
        }else {
           throw new RuntimeException("Account not found");
        }
    }

    private List<Account> newAccountsFromPayload(List<Map<String, Object>> payload) {
        if (payload == null || payload.isEmpty()) {
            return null;
        }

        List<Account> follows = new ArrayList<>();
        for (Map<String, Object> accountPayload : payload) {
            String id = (String) accountPayload.get("id");
            String username = (String) accountPayload.get("username");
            String displayName = (String) accountPayload.get("displayName");

            Optional<Account> existingFollow = accountRepository.findById(id);
            Account follow = existingFollow.orElse(new Account(id, username, displayName));

            follows.add(follow);
        }

        return follows;
    }

}
