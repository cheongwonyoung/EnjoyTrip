package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.board.model.BoardContentDto;
import com.ssafy.board.model.BoardDto;

public interface BoardService {
	List<BoardDto> getList() throws Exception;
	BoardDto view(int planId) throws Exception;
	void write(BoardDto boardDto) throws Exception;
	void delete(int planId) throws Exception;
	String getName(int planId) throws Exception;
	List<AttractionDto> getAttractions(String search) throws Exception;
	String writePlanBoard(BoardDto boardDto) throws Exception;
	void writePlanContent(List<String []> contents) throws Exception;
	void updateHit(int planNo) throws Exception;
	BoardContentDto getContent(Map<String, Integer> map);
	List<Integer> getContentIds(int planId);
	void write2(BoardDto board);
}
