package com.order.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.api.enums.GenderKind;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Member extends CommonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(length = 30)
    @Setter
    private String name;

    @Column(length = 30)
    @Setter
    private String nickName;

    @JsonIgnore
    private String password;

    @Column(length = 30)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String gender;

    @Setter
    @Transient
    private String jwtToken;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    private List<Orders> orderList = new ArrayList<>();

    @Builder
    public Member (String name , String nickName , String password , String phoneNumber , String email , String gender){
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }






}
