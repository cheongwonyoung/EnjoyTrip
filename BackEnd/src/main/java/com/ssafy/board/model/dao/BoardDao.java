package com.ssafy.board.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.board.model.BoardContentDto;
import com.ssafy.board.model.BoardDto;

@Mapper
public interface BoardDao {

	List<BoardDto> getList() throws SQLException;
	BoardDto view(int planId) throws SQLException;
	void write(BoardDto boardDto) throws SQLException;
	void delete(int planId) throws SQLException;
	String getName(int planId) throws SQLException;
	List<AttractionDto> getAttractions(String search) throws SQLException;
	String writePlanBoard(BoardDto boardDto) throws SQLException;
	void writePlanContent(String[] content) throws SQLException;
	void updateHit(int planNo) throws SQLException;
	BoardContentDto getContent(Map<String, Integer> map);
	List<Integer> getContentIds(int planId);
	void write2(BoardDto board);
}
