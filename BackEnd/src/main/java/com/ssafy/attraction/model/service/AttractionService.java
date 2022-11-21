package com.ssafy.attraction.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.AreacodeDto;
import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.SigungucodeDto;

public interface AttractionService {
	List<AreacodeDto> getAreacode() throws Exception;
	List<SigungucodeDto> getSigungucode(int regcode) throws Exception;
	List<AttractionDto> getAttractions(int areacode, int sigungucode) throws Exception;
	List<AttractionDto> getDefaultAttractions(Map<String, Double> map) throws Exception;
	List<AttractionDto> getAttractions(int areacode, int sigungucode, int optioncode) throws Exception;
	AttractionDto getAttraction(int contentid) throws Exception;
	List<AttractionDto> getAttractions(Map<String, Integer> map);
	List<AttractionDto> search(String search);
	List<AttractionDto> getAttractionsWithOption(Map<String, Integer> map);
}
