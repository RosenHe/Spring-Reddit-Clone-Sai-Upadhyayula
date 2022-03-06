package com.example.communities_post_website.service;

import com.example.communities_post_website.model.User;
import com.example.communities_post_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /*The UserDetailsService is a core interface in Spring Security framework,
    which is used to retrieve the user's authentication and authorization information.
    * */
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("Can't find this username with name - " + username) );

        //here mapping the user detail to the user class.
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.isEnabled(),
                true,true,
                true, getAuthorities("USER"));
    }
    /*Provide an Authorities for a Role call User.
    * */
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        //SimpleGrantedAuthority is Stores a String representation of an
        //authority granted to the Authentication object.
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
