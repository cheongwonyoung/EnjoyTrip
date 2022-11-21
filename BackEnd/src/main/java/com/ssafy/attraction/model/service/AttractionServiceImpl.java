package com.ssafy.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.attraction.model.AreacodeDto;
import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.SigungucodeDto;
import com.ssafy.attraction.model.dao.AttractionDao;

@Service
public class AttractionServiceImpl implements AttractionService {

	private AttractionDao attractionDao;
	
	@Autowired
	private AttractionServiceImpl(AttractionDao attractionDao) {
		this.attractionDao = attractionDao;
	}

	@Override
	public List<AreacodeDto> getAreacode() throws Exception {
		return attractionDao.getAreacode();
	}

	@Override
	public List<SigungucodeDto> getSigungucode(int regcode) throws Exception {
		return attractionDao.getSigungucode(regcode);
	}

	@Override
	public List<AttractionDto> getAttractions(int areacode, int sigungucode) throws SQLException {
		return attractionDao.getAttractions(areacode, sigungucode);
	}

	@Override
	public List<AttractionDto> getDefaultAttractions(Map<String, Double> map) throws SQLException {
		return attractionDao.getDefaultAttractions(map);
	}

	@Override
	public List<AttractionDto> getAttractions(int areacode, int sigungucode, int optioncode) throws Exception {
		return attractionDao.getAttractions(areacode, sigungucode, optioncode);
	}

	@Override
	public AttractionDto getAttraction(int contentid) throws Exception {
		return attractionDao.getAttraction(contentid);
	}

	@Override
	public List<AttractionDto> getAttractions(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return attractionDao.getAttractions(map);
	}

	@Override
	public List<AttractionDto> search(String search) {
		// TODO Auto-generated method stub
		return attractionDao.search(search);
	}

	@Override
	public List<AttractionDto> getAttractionsWithOption(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return attractionDao.getAttractionsWithOption(map);
	}
}
