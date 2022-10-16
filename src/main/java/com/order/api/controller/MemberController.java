package com.order.api.controller;


import com.order.api.common.RestResponse;
import com.order.api.domain.Member;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import com.order.api.security.JwtTokenProvider;
import com.order.api.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    @ApiOperation("회원 가입 - 고유정보인 로그인 아이디를 포함한 기본정보로 가입.")
    @PostMapping("/join")
    public ResponseEntity<RestResponse> join(
            @ApiParam("비고 : 핸드폰 형식 (-) 포함 시켜야 합")
            @RequestBody JoinForm joinForm
    ) throws Exception {

        return ResponseEntity.ok(RestResponse.ok(memberService.join(joinForm)));
    }

    @ApiOperation("로그인 - ( 아이디 와 패스워드로 인해 인증. 인증 성공 시 결과값으로 토큰 획득 )")
    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(
            @RequestBody LoginForm loginForm
    ) throws Exception {


        Member member = memberService.login(loginForm);
        member.setJwtToken(jwtTokenProvider.createToken(member.getLoginId() , member.getRoles()));

        return ResponseEntity.ok(RestResponse.ok(member));
    }

    @ApiOperation("단일 회원 상세 정보 조회")
    @GetMapping("/{loginId}/detail")
    public ResponseEntity<RestResponse> detail(
           @PathVariable String loginId
    ) throws Exception {

        return ResponseEntity.ok(RestResponse.ok(memberService.getMember(loginId)));
    }

    @ApiOperation("단일 회원 주문 목록 조회")
    @GetMapping("/{loginId}/orders")
    public ResponseEntity<RestResponse> orders(
            @PathVariable String loginId
    ) throws Exception {

        return ResponseEntity.ok(RestResponse.ok(memberService.getMember(loginId).getOrderList()));
    }


    @ApiOperation("여러 회원 조회 - 페이지네이션 , ( 이름 , 이메일 ) 검색  , 멤버의 마지막 주문정보 포함.")
    @GetMapping("/all")
    public ResponseEntity<RestResponse> all(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(value = "page" , defaultValue = "1") Integer page,
            @RequestParam(value = "size" , defaultValue = "20") Integer size
    ) throws Exception {

        return ResponseEntity.ok(RestResponse.ok(
                memberService.getAllMember(name, email ,
                        PageRequest.of(page-1 , size , Sort.by("memberSeq").descending()
                        ))));
    }






}
