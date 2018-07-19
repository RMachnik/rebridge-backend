package application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class CurrentUser implements UserDetails {

    String id;
    @JsonProperty(required = true)
    String email;
    String password;
    Set<String> roles;

    @JsonCreator
    CurrentUser(@JsonProperty("id") final String id,
                @JsonProperty("email") final String email,
                @JsonProperty("password") final String password,
                Set<String> roles) {
        super();
        this.id = requireNonNull(id);
        this.email = requireNonNull(email);
        this.password = requireNonNull(password);
        this.roles = roles;
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
        return password;
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
    static class Authority implements GrantedAuthority {
        String authority;

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
