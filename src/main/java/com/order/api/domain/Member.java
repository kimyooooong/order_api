package com.order.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long userId;

    @Column(length = 20)
    @Setter
    private String name;

    @Column(length = 30)
    @Setter
    private String nickName;

    @JsonIgnore
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private String gender;

    @Setter
    @Transient
    private String jwtToken;

    @JsonManagedReference
    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    private List<Order> orderList = new ArrayList<>();

}
