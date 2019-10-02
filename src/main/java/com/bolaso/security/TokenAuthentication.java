package com.bolaso.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final DecodedJWT jwt;
    private boolean invalidated;

    public TokenAuthentication(DecodedJWT jwt) {
        super(readAuthorities(jwt));
        this.jwt = jwt;
    }

    private boolean hasExpired() {
        return jwt.getExpiresAt().before(new Date());
    }

    private static Collection<? extends GrantedAuthority> readAuthorities(DecodedJWT jwt) {
        Claim roles = jwt.getClaim("https://access.control/roles");

        if( roles.isNull() ) {
            return Collections.emptyList();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] scopes = roles.asArray(String.class);

        for( String scope : scopes ) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(scope);
            if( !authorities.contains(sga) ) {
                authorities.add(sga);
            }
        }

        return authorities;
    }

    @Override
    public Object getCredentials() {
        return jwt.getToken();
    }

    @Override
    public Object getPrincipal() {
        return jwt.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return !invalidated && !hasExpired();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if( authenticated ) {
            throw new IllegalArgumentException("Create a new Authentication object to authenticate.");
        }

        invalidated = true;
    }

    /**
     * Gets the claims for this JWT token.
     * <br>
     * For an ID token, claims represent user profile information such as the user's name, profile, picture, etc.
     * <br>
     * @see <a href="https://auth0.com/docs/tokens/id-token">ID Token Documentation</a>
     * @return a Map containing the claims of the token.
     */
    public Map<String, Claim> getClaims() {
        return jwt.getClaims();
    }
}
