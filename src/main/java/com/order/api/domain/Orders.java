package com.order.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSeq;

    @Column(length = 30 , unique = true)
    private String orderNumber;

    @Column(length = 100)
    private String name;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name ="memberSeq")
    @JsonBackReference
    private Member member;

    private LocalDateTime createDate;

    @PrePersist
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Orders(Member member , String orderNumber ,String name){
        this.member =member;
        this.orderNumber = orderNumber;
        this.name = name;
    }

}
