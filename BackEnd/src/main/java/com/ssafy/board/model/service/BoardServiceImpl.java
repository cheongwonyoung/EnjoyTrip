package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.board.model.BoardContentDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.dao.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{

	private BoardDao boardDao;
	
	@Autowired
	private BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@Override
	public List<BoardDto> getList() throws Exception {
		return boardDao.getList();
	}

	@Override
	public String getName(int planId) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.getName(planId);
	}

	@Override
	public BoardDto view(int planId) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.view(planId);
	}

	@Override
	public void write(BoardDto boardDto) throws Exception {
		// TODO Auto-generated method stub
		boardDao.write(boardDto);
	}

	@Override
	public void delete(int planId) throws Exception {
		boardDao.delete(planId);
	}

	@Override
	public List<AttractionDto> getAttractions(String search) throws Exception {
		return boardDao.getAttractions(search);
	}

	@Override
	public String writePlanBoard(BoardDto boardDto) throws Exception {
		return boardDao.writePlanBoard(boardDto);
	}

	@Override
	public void writePlanContent(List<String[]> contents) throws Exception {
		for (int i = 0; i < contents.size(); i++) {
			boardDao.writePlanContent(contents.get(i));
		}
	}

	@Override
	public void updateHit(int planNo) throws Exception {
		boardDao.updateHit(planNo);
	}

	@Override
	public BoardContentDto getContent(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return boardDao.getContent(map);
	}

	@Override
	public List<Integer> getContentIds(int planId) {
		// TODO Auto-generated method stub
		return boardDao.getContentIds(planId);
	}

	@Override
	public void write2(BoardDto board) {
		// TODO Auto-generated method stub
		boardDao.write2(board);
	}


}
