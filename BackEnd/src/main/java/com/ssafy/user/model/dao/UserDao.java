package com.ssafy.user.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user.model.UserDto;

@Mapper
public interface UserDao {
	void regist(UserDto userDto) throws SQLException;
	int idCheck(String userId) throws SQLException;
	UserDto login(Map<String, String> map) throws SQLException;
	void editProfile(UserDto user) throws SQLException;
	void delete(String userId) throws SQLException;
	String find(Map<String, String> map) throws SQLException;
	UserDto getMember(String userId );
	void deleteRefreshToken(Map<String, String> map);
	Object getRefreshToken(String userid);
	void saveRefreshToken(Map<String, String> map);
	UserDto userInfo(String userid);
}
