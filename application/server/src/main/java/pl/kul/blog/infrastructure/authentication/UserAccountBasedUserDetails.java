package pl.kul.blog.infrastructure.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kul.blog.domain.user.account.UserAccount;
import pl.kul.blog.domain.user.account.UserAccountAssignedRole;

import java.util.Collection;
import java.util.stream.Collectors;

class UserAccountBasedUserDetails implements UserDetails {
    private final UserAccount userAccount;

    UserAccountBasedUserDetails(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getRoles()
            .stream()
            .map(UserAccountAssignedRole::getRole)
            .map(GrantedAuthorites::from)
            .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userAccount.getIsExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userAccount.getIsAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userAccount.getIsCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return userAccount.getIsEnabled();
    }

}
