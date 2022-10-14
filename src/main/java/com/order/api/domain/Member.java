package com.order.api.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.api.enums.GenderKind;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({ "roles", "enabled" ,"authorities" , "username" , "accountNonLocked" , "credentialsNonExpired" , "accountNonExpired" , "orderList" })
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(length =30 , unique = true)
    @Setter
    private String loginId;

    @Column(length = 30)
    @Setter
    private String name;

    @Column(length = 30)
    @Setter
    private String nickName;

    @JsonIgnore
    private String password;

    @Column(length = 30)
    @Setter
    private String phoneNumber;

    @Column(length = 100)
    @Setter
    private String email;

    @Column(length = 30)
    @Setter
    private String gender;

    @Setter
    @Transient
    private String jwtToken;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    private List<Orders> orderList = new ArrayList<>();


    private LocalDateTime createDate;


    @Transient
    private List<String> roles = new ArrayList<>();

    @Builder
    public Member (String loginId , String name , String nickName , String password , String phoneNumber , String email , String gender){
        this.loginId = loginId;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }

    @PrePersist
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
