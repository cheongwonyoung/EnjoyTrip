package com.ssafy.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.board.model.BoardContentDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.board.model.service.BoardServiceImpl;
import com.ssafy.user.model.UserDto;

import net.bytebuddy.description.field.FieldDescription.InGenericShape;

//@WebServlet("/board")
@RestController
@RequestMapping("/boardcon")
@CrossOrigin("*")
public class BoardController extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final long serialVersionUID = 1L;

	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		logger.info("BoardController 생성 완료");
		this.boardService = boardService;
	}

	@GetMapping("/mvlist")
	public ModelAndView mvlist() {
		logger.debug("mvlist!!!!");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/list");

		return mav;
	}

	@GetMapping("/list")
	public ResponseEntity<?> list() throws Exception {
		try {
			List<BoardDto> list = boardService.getList();
			logger.debug("여기 옴" + list.get(0).getPlanId());
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	@PostMapping("/list")
	public ResponseEntity<?> write(@RequestBody Map<String, Object> map, HttpServletRequest request) throws Exception {
		logger.debug("작성되는 중?");
		logger.debug("title");
		logger.debug((String) map.get("title"));
		logger.debug("thumbnail");
		logger.debug((String) map.get("thumbnail"));
		logger.debug("attIdList");
		logger.debug(map.get("attIdList").toString());
		logger.debug("attDescList");
		logger.debug(map.get("attDescList").toString());
		logger.debug("userid");
		logger.debug(map.get("userId").toString());
		
		HttpSession session = request.getSession();
//		UserDto user = (UserDto) session.getAttribute("userInfo");

//		if (user != null) {
			BoardDto board = new BoardDto();
			board.setTitle((String) map.get("title"));
			board.setThumbNail((String) map.get("thumbnail"));
			board.setUserId((String)map.get("userId"));
//			board.setUserId("임시 아이디입니다");
			
			ArrayList<Integer> attIds = (ArrayList<Integer>) map.get("attIdList");
			ArrayList<String> attDescs = (ArrayList<String>) map.get("attDescList");
			List<BoardContentDto> contents = new ArrayList<>();
			int size = attIds.size();
			logger.debug("여기요");
//			logger.debug(attIds.toString());
//			logger.debug(attIds.get(0));
			logger.debug(Integer.toString(size));
			for (int i = 0; i < size; i++) {
				String id = "attractionId" + i;
				String desc = "attractionDesc" + i;
				BoardContentDto bcd = new BoardContentDto();
				bcd.setContentId(attIds.get(i));
				bcd.setContentDesc(attDescs.get(i));
				contents.add(bcd);
			}

			board.setContentList(contents);
			boardService.write(board);
			boardService.write2(board);

//		}
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@GetMapping("/mvview/{planId}")
	public ModelAndView mvview(@PathVariable("planId") int planId) {
		ModelAndView mav = new ModelAndView();
		logger.debug("여기 찍힌대!! view로 갈거야!! " + planId);
		mav.addObject("planId", planId);
		mav.setViewName("/board/view");

		return mav;
	}

	@GetMapping("/mvwrite")
	public ModelAndView mvwrite() {
		ModelAndView mav = new ModelAndView();
		logger.debug("write로 이동!!");
		mav.setViewName("/board/write");

		return mav;
	}

	@GetMapping("/view/{planId}")
	public ResponseEntity<?> view(@PathVariable("planId") int planId, Model model) throws Exception {
		try {
			logger.debug("view!!!");
			BoardDto boardDto = boardService.view(planId);
			List<Integer> contentIdList = boardService.getContentIds(planId);
			logger.debug("size: " + contentIdList.size());
			List<BoardContentDto> list = new ArrayList<BoardContentDto>();
			for (int i = 0; i < contentIdList.size(); i++) {
				Map<String, Integer> map = new HashMap<>();
				map.put("contentId", contentIdList.get(i));
				map.put("planId", planId);
				BoardContentDto boardContentDto = boardService.getContent(map);
				list.add(boardContentDto);
			}
			boardDto.setContentList(list);

			if (boardDto != null) {
				boardService.updateHit(planId);
				return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/list/{planId}")
	public ResponseEntity<?> delete(@PathVariable("planId") int planId, Model model) throws Exception {
		logger.debug("삭제로 왜 안와? : " + Integer.toString(planId));
		boardService.delete(planId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private ResponseEntity<?> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
