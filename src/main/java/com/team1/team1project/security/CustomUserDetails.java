package com.team1.team1project.security;

import com.team1.team1project.domain.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Login login;

    public CustomUserDetails(Login login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(login.getEmployee() != null && "팀장".equals(login.getEmployee().getPosition())){
            authorities.add(new SimpleGrantedAuthority("ROLE_TEAMLEADER"));
        }
        return authorities;

    }

    @Override
    public String getPassword() {
        return login.getPassword();
    }

    @Override
    public String getUsername() {
        return login.getLoginId();
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
        return true;
    }

    // Login 객체 직접 접근용 getter
    public Login getLogin() {
        return login;
    }

    // Employee 이름 가져오는 편의 메소드
    public String getEmployeeName() {
        return login.getEmployeeName();
    }

    // Employee ID 가져오는 편의 메소드
    public int getEmployeeId() {
        return login.getEmployee().getEmployeeId();
    }
    
    // Employee Position 가져오는 편의 메소드
    public String getPosition() {
        return login.getEmployee().getPosition();
    }

    //
    public String getDepartment() {
        return login.getEmployee().getDepartment();
    }
}