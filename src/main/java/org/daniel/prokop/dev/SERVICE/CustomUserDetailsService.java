package org.daniel.prokop.dev.SERVICE;

import org.daniel.prokop.dev.DAO.Users;
import org.daniel.prokop.dev.REPO.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Users customer = userRepo.findUserByUsername(username).get();
        if (customer == null){
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = User.withUsername(customer.getUsername())
                .password(customer.getPassword())
                .authorities("ROLE_"+customer.getRoles().toString()).build();
        return user;
    }
}
