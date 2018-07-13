package application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class CurrentUser implements UserDetails {

    String id;
    @JsonProperty(required = true)
    String username;
    String password;

    @JsonCreator
    CurrentUser(@JsonProperty("id") final String id,
                @JsonProperty("username") final String username,
                @JsonProperty("password") final String password) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
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
}
