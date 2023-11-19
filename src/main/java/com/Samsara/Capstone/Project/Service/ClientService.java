package com.Samsara.Capstone.Project.Service;

import com.Samsara.Capstone.Project.Model.Client;
import com.Samsara.Capstone.Project.Model.WishList;
import com.Samsara.Capstone.Project.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final WishListService wishListService;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, WishListService wishListService){
        this.clientRepository=clientRepository;
        this.passwordEncoder=passwordEncoder;
        this.wishListService = wishListService;
    }

    public Optional<Client> findClientByUserName(String username){
        return clientRepository.findUserByUserName(username);
    }

    public Boolean userNameExists(String userName){
        return clientRepository.existsByUserName(userName);
    }

    public Optional<Client> findClientByEmail(String email){
        return clientRepository.findUserByEmail(email);
    }


    public Boolean userEmailExists(String email){
        return clientRepository.existsByEmail(email);
    }
    public Boolean userPhoneNumberExists(String number){ return clientRepository.existsByPhoneNumber(number);}

    public List<Client> getAllUsers(){
        return clientRepository.findAll();
    }

    public Client getClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client){
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        WishList wishList = new WishList();
        wishList = wishListService.createWish(wishList);
        client.setWishList(wishList);
        return clientRepository.save(client);
    }

    public Client updateClient(Client client){
        return clientRepository.save(client);
    }
    public void deleteUser(Long id){

        clientRepository.deleteById(id );
    }
    @Transactional
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
//    public Client deActivateUser(UUID id){
//        Optional<Client> user = clientRepository.findById(id);
//        user.ifPresent(value -> value.setEnabled(false));
//        return user.orElse(null);
//    }
//
//    public User activate(UUID id){
//        Optional<User> user = userRepository.findById(id);
//        user.ifPresent(value -> value.setEnabled(true));
//        return user.orElse(null);
//    }
//
//    public boolean validId(UserDetails userDetails, String id){
//        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
//        return userInfoDetails.getUserId().equals(id);
//    }

}
