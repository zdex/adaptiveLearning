package com.adap.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentDto {
    private long parentId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String emailId;
    private boolean accountExpired;
    private boolean  accountLocked;
    private boolean  credentialsExpired;
    private boolean  disabled;
    private List<String> roles;
    private List<StudentDTO> students;
    Collection<? extends GrantedAuthority> userAuthorities;
    private int phonenumber;

}
