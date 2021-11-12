package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.dto.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService){

        this.userService = userService;

    }


    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> signUp(@RequestBody PostUserReq postUserReq){


        // email에 값이 존재하는지, 빈 값으로 요청하지는 않았는지 검사합니다. 빈값으로 요청했다면 에러 메시지를 보냅니다.
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try {
            // 이메일 형식 확인
            if(!isRegexEmail(postLoginReq.getEmail()))throw new BaseException(POST_USERS_INVALID_EMAIL);

            //유저 status 확인
            if(!userService.checkUserStatus(postLoginReq))throw new BaseException(POST_USERS_INVALID_STATUS);

            PostLoginRes postLoginRes = userService.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }


    //id로 본인 조회
    @GetMapping("/user/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx){
        try{
            GetUserRes getUserRes = userService.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //유저 정보 수정, 이메일,네임,주소
    @PatchMapping("/user/{userIdx}")
    public BaseResponse<String> modifyUser(@PathVariable("userIdx")int userIdx, @RequestBody User user){
        try{
                PatchUserReq patchUserReq = new PatchUserReq(userIdx,user);

                if(patchUserReq.getName() != null){
                        userService.modifyUserName(patchUserReq);
                }
                if(patchUserReq.getEmail() != null){
                    userService.modifyEmail(patchUserReq);
                }
                if(patchUserReq.getZipcode() != null){
                    userService.modifyZipcode(patchUserReq);
                }

                return new BaseResponse<>("회원정보 수정 완료!!");


        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }



}
