package com.example.LServer.module.Users;

import com.example.LServer.constant.Users.UserAccountType;
import com.example.LServer.repository.Users.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class UsersAccount {

    private final UserAccountRepository userAccountRepository;

    /**
     * 로그인 아이디 생성 규칙 확인
     * <hr>
     *     규칙
     *     <li>영문 대문자 제외</li>
     *     <li>특수문자 제외</li>
     * @param id
     * @return
     */
    public boolean isValidID(String id, UserAccountType type) throws Exception {
        String regex = "^[a-z0-9]{1}[a-z0-9]{5,20}$";
        if(!id.matches(regex)) {
            throw new Exception("잘못된 ID입니다. ID는 6~20자 이하이며, 소문자와 숫자 조합이어야 합니다.");
        }

        if (!ObjectUtils.isEmpty(userAccountRepository.findUserAccountEntityByLoginIdAndType(id, type))) {
            throw new Exception("사용할 수 없는 아이디 입니다.");
        }
        return true;
    }

    /**
     * 로그인 패스워드 생성 규칙 확인
     * <hr>
     *     규칙
     *     <li>영문자 1개 이상</li>
     *     <li>숫자 1개 이상</li>
     *     <li>특수문자 1개 이상</li>
     * @param password
     * @return
     * @throws Exception
     */
    public boolean isValidPassword(String password) throws Exception {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!.%*#?&])[A-Za-z\\d$@$!.%*#?&]{9,20}$";
        if(!password.matches(regex)) {
            throw new Exception("유효하지 않은 비밀번호입니다. 비밀번호는 최소한 10~20이하이며, 문자로 시작하고 하나이상의 영문자, 하나이상의 숫자, 지정된 특수문자($ @ ! . * # ? &)중 하나 이상을 포함해야합니다.");
        }
        return true;
    }
}
