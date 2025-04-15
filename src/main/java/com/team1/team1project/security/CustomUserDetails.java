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

        // 기본적으로 모든 사용자에게 USER 권한 부여
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // position 정보가 있는지 확인 후 처리
        if(login.getEmployee() != null) {
            String position = login.getEmployee().getPosition();

            // 디버깅을 위한 로그 추가
            System.out.println("현재 로그인 사용자 정보:");
            System.out.println("- ID: " + login.getLoginId());
            System.out.println("- 직급: " + position);

            // 직급이 null이 아니고, 값이 있는 경우 처리
            if(position != null && !position.trim().isEmpty()) {
                // 대소문자 구분 없이, 앞뒤 공백 제거 후 '팀장'인지 확인
                if("팀장".equals(position.trim())) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_TEAMLEADER"));
                    System.out.println("ROLE_TEAMLEADER 권한이 추가되었습니다!");
                }
            }
        }

        // 최종 부여된 권한 로그 출력
        System.out.println("최종 부여된 권한: " + authorities);

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