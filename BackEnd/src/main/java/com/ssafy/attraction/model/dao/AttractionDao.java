package com.ssafy.attraction.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.model.AreacodeDto;
import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.SigungucodeDto;

@Mapper
public interface AttractionDao {
	List<AreacodeDto> getAreacode() throws SQLException;
	List<SigungucodeDto> getSigungucode(int regcode) throws SQLException;
	List<AttractionDto> getAttractions(int areacode, int sigungucode) throws SQLException;
	List<AttractionDto> getDefaultAttractions(Map<String, Double> map) throws SQLException;
	List<AttractionDto> getAttractions(int areacode, int sigungucode, int optioncode) throws SQLException;
	AttractionDto getAttraction(int contentid) throws SQLException;
	List<AttractionDto> getAttractions(Map<String, Integer> map);
	List<AttractionDto> search(String search);
	List<AttractionDto> getAttractionsWithOption(Map<String, Integer> map);
}
