package com.example.shopgiayvip.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class NguoiDungDetails implements UserDetails {
    private NguoiDung user;
    private Collection<? extends GrantedAuthority> authorities;

    public NguoiDungDetails() {
    }

    public NguoiDungDetails(NguoiDung user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public NguoiDung getUser() {
        return user;
    }

    public void setUser(NguoiDung user) {
        this.user = user;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getMatKhau();
    }

    @Override
    public String getUsername() {
        return user.getEnmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getTrangThai();
    }
}
