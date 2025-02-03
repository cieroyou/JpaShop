package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        memberService.join(member);

        // then
        em.flush(); // 테스트에서 쿼리로그를 보고 싶은 경우 entitymember.flush()를 사용하면 됨
        assertEquals(member, memberRepository.findOne(member.getId()));
    }

    @Test
    void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member joinMember = new Member();
        joinMember.setName("kim");

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(joinMember)); // 예외가 발생해야 함
    }
}