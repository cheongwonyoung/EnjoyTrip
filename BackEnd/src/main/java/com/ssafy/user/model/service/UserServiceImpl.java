package com.ssafy.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
		
	@Autowired
	private UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void regist(UserDto userDto) throws Exception {
		userDao.regist(userDto);		
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return userDao.idCheck(userId);
	}

	@Override
	public UserDto login(Map<String, String> map) throws Exception {
		return userDao.login(map);
	}

	@Override
	public void editProfile(UserDto user) throws Exception {

		userDao.editProfile(user);
	}

	@Override
	public void delete(String userId) throws Exception {

		userDao.delete(userId);
	}

	@Override
	public String find(Map<String, String> map) throws Exception {
		
		return userDao.find(map);
	}

	@Override
	public UserDto getMember(String userId ) {
		return userDao.getMember(userId);
	}


	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserDao.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(UserDao.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(UserDao.class).deleteRefreshToken(map);
	}

	@Override
	public UserDto userInfo(String userid) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(UserDao.class).userInfo(userid);
	}
	
}
