package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ClientDetService {
    private final ClientRepository clientRepository;

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION =  60 * 1000; // 1 minute


    @Autowired
    public ClientDetService(ClientRepository clientRepository){
        this.clientRepository=clientRepository;

    }
    public Optional<Client> findClientByUserName(String username){
        return clientRepository.findUserByUserName(username);
    }
    public void increaseFailedAttempts(Client client) {
        int newFailAttempts = client.getFailedAttempt() + 1;
        client.setFailedAttempt(newFailAttempts);
        clientRepository.save(client);
    }

    public void lock(Client client) {
        client.setAccountNonLocked(false);
        client.setLockTime(new Date());
        clientRepository.save(client);
    }

    public boolean unlockWhenTimeExpired(Client client) {
        long lockTimeInMillis = client.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            client.setAccountNonLocked(true);
            client.setLockTime(null);
            client.setFailedAttempt(0);

            clientRepository.save(client);
            return true;
        }
        return false;
    }


    public void resetFailedAttempts(String name) {
        Client client = clientRepository.findUserByUserName(name).orElse(null);
        if(client == null)
            return;
        client.setFailedAttempt(0);
        clientRepository.save(client);

    }

}
