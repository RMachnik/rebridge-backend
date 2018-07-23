package application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class CurrentUser implements UserDetails {

    String id;
    @JsonProperty(required = true)
    String email;
    String token;
    Set<String> roles;

    public CurrentUser(String id,
                       String email,
                       String token,
                       Set<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(Authority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return "";
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Value
    public static class Authority implements GrantedAuthority {
        String authority;

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
