package in.sanket.ExpenseTrackerApi.security;

import in.sanket.ExpenseTrackerApi.entity.User;
import in.sanket.ExpenseTrackerApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existinguser=userRepository
        .findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email "+email));
       return new org.springframework.security.core.userdetails.User(existinguser.getEmail(), existinguser.getPassword(), new ArrayList<>());
    }
}
