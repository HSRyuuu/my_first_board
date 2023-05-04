package hello.board.service.login;

import hello.board.domain.member.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * findByLoginId로 멤버를 찾아서 getPassword로 비번을 받아서 입력받은 password와 일치하는지 확인하여 반환
     * 없으면 null 반환
     */
    public Member login(String loginId, String password){
        return memberRepository.findByLoginId(loginId)
                .filter(m-> m.getPassword().equals(password))
                .orElse(null);
    }
}
