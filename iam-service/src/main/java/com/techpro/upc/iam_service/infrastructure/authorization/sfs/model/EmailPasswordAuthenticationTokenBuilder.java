package com.techpro.upc.iam_service.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


public class EmailPasswordAuthenticationTokenBuilder {

    public static UsernamePasswordAuthenticationToken build(UserDetails principal, HttpServletRequest request) {
        var emailPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        emailPasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return emailPasswordAuthenticationToken;
    }
}