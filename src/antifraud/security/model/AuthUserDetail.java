package antifraud.security.model;

import antifraud.constant.UserAccountStatus;
import antifraud.entity.auth.UserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthUserDetail implements UserDetails {

    private String password;

    private String username;

    private UserAccountStatus status;

    private List<GrantedAuthority> authorities;

    public AuthUserDetail(UserDetail user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().getName().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserAccountStatus.UNLOCK.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
