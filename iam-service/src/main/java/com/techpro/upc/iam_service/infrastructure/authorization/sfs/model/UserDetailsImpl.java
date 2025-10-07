package com.techpro.upc.iam_service.infrastructure.authorization.sfs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techpro.upc.iam_service.domain.model.aggregates.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;



@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private final String email;
    @JsonIgnore
    private final String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }



    public static UserDetailsImpl build(User user) {
        // As we don't need roles, return an empty list of authorities
        Collection<GrantedAuthority> authorities = Collections.emptyList();  // No authorities are assigned
        return new UserDetailsImpl(
                user.getEmail(), // Use email as the identifier
                user.getPassword(),
                authorities);
    }


    @Override
    public String getUsername() {
        return email; // Return email as username
    }

    // Optionally override other methods as needed, but they're already set to "true" by default.
}