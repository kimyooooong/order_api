package com.order.api.form;

import com.order.api.enums.GenderKind;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinForm {
    private String loginId;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private String email;
    private GenderKind genderKind;

}
