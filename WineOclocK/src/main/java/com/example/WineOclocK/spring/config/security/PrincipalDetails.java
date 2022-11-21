package com.example.WineOclocK.spring.config.security;

import com.example.WineOclocK.spring.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Security Session => Authentication => UserDetails
public class PrincipalDetails implements UserDetails {

    private User user; // 콤포지션

    // 생성자 만들기
    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override // 어떤 권한을 가졌니?
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(user.getRole()));
        return authList;

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override // 만료 안 됐는지
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 락 안 걸렸는지
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정활성화 되어있는지
    public boolean isEnabled() { return true; }
}
