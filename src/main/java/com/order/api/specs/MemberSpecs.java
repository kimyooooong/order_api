package com.order.api.specs;

import com.order.api.domain.Member;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecs {

    public static Specification<Member> add(final String key , final String value) {
        return (root, query, cb) -> cb.equal(root.get(key), value);
    }

    public static Specification<Member> name(final String name) {

        return (root, query, cb) -> cb.equal(root.get("name"), name);

    }

    public static Specification<Member> email(final String email) {

        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

}
