package net.bigmir.services;

import net.bigmir.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        SimpleUser simpleUser = userService.findByLogin(login);
        if(simpleUser == null){
            throw new UsernameNotFoundException(login + " was not found");
        }
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(simpleUser.getRole().toString()));
        return new User(simpleUser.getLogin(),simpleUser.getPassword(),roles);
    }
}
