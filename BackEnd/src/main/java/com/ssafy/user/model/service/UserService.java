package com.ssafy.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.user.model.UserDto;

public interface UserService {
	void regist(UserDto userDto) throws Exception;
	int idCheck(String userId) throws Exception;
	UserDto login(Map<String, String> map) throws Exception;

	void editProfile(UserDto user) throws Exception;
	void delete(String userPwd) throws Exception;
	String find(Map<String, String> map) throws Exception;
	UserDto getMember(String userId );
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	UserDto userInfo(String userid);
}
