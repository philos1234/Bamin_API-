package com.example.demo.src.user;

import com.example.demo.src.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into user (password,email,`name`,zipcode) VALUES (?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createUserParams = new Object[]{postUserReq.getPassword(), postUserReq.getEmail(), postUserReq.getName(),postUserReq.getZipcode()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.

    }

    // 회원정보 변경
    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update User set `name` = ? where ID = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getName(), patchUserReq.getUserIdx()}; // 주입될 값들(nickname, userIdx) 순
        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    //이메일 변경
    public int modifyEmail(PatchUserReq patchUserReq){
        String modifyEmailQuery = "update User set `email` = ? where ID = ?";
        Object[] modifyEmailParams = new Object[]{patchUserReq.getEmail(),patchUserReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyEmailQuery, modifyEmailParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }


    //주소 변경
    public int modifyZipcode(PatchUserReq patchUserReq){
        String modifyZipcodeQuery = "update User set `zipcode` = ? where ID = ?";
        Object[] modifyZipcodeParams = new Object[]{patchUserReq.getZipcode(),patchUserReq.getUserIdx()};
        return this.jdbcTemplate.update(modifyZipcodeQuery, modifyZipcodeParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }



    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select ID, password,email,`name`,zipcode from user where email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postLoginReq.getEmail(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("ID"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("zipcode")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    // 해당 userIdx를 갖는 유저조회
    public GetUserRes getUser(int userIdx) {
        String getUserQuery = "select * from user where ID = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("zipcode")
                ),
                        // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    //유저 리스트 조회 5개씩
    public List<GetUserRes> getUserResList(int page){
        String getUserListQuery = "select * from user limit 3 offset ?";
        int param = page;
        return this.jdbcTemplate.query(getUserListQuery,(rs, rowNum) ->{
            GetUserRes t = new GetUserRes();
            t.setUserIdx(rs.getInt("id"));
            t.setName(rs.getString("name"));
            t.setEmail(rs.getString("email"));
            return t;
        },page);

    }


    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from user where email = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        String checkEmailParams = email; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    public String checkUserStatus(PostLoginReq postLoginReq){

        String getStatusQuery = "select status from user where email = ?";
        String checkUserStatusParam = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getStatusQuery, String.class,
                checkUserStatusParam);

    }




}
