package com.ssafy.attraction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.attraction.model.AreacodeDto;
import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.SigungucodeDto;
import com.ssafy.attraction.model.service.AttractionService;
import com.ssafy.board.controller.BoardController;

//@WebServlet("/attraction")
@RestController
@RequestMapping("/attraction")
@CrossOrigin("*")
public class AttractionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AttractionService attractionService;
	
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	public AttractionController(AttractionService attractionService) {
		logger.info("AttractionController 생성 완료");
		this.attractionService = attractionService;
	}

	// 검색어로 조회
	@GetMapping("/search/{search}")
	public ResponseEntity<?> search(@PathVariable("search") String search){
		try {
			logger.debug("검색중? " + search);
			List<AttractionDto> attractionList = attractionService.search(search);
			if(attractionList != null) {
				return new ResponseEntity<List<AttractionDto>>(attractionList, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("detail/{contentId}")
	   private ResponseEntity<?> detail(@PathVariable("contentId") int contentId) {
	      try {
	         logger.debug("detail contentId  : " + contentId );
	         
	         AttractionDto attraction = attractionService.getAttraction(contentId);
	         
	         if (attraction != null) {
	            return new ResponseEntity<AttractionDto>(attraction, HttpStatus.OK);
	         } else {
	            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	         return exceptionHandling(e);
	      }
	   
	}

	
	// areaCode 가지고 attraction-top30으로 가기
	@GetMapping("/attraction")
	private ModelAndView mvAttraction() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/attraction/attraction-top30");
		
		return mav;
	}
	
	@GetMapping("/sido")
	private ResponseEntity<?> getAreaCode() throws Exception {
		try {
			List<AreacodeDto> areas = attractionService.getAreacode();
			
			if (areas != null && !areas.isEmpty()) {
				return new ResponseEntity<List<AreacodeDto>>(areas, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	// areaCode 가지고 attraction-top30으로 가기
	@GetMapping("/facilities")
	private ModelAndView mvFacilities() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/attraction/nearby-facilities");
			
		return mav;
	}
	
	@GetMapping("/recommend/{sidocode}/{guguncode}/{optioncode}")
	private ResponseEntity<?> recommendWithOption(@PathVariable("sidocode") int areacode, @PathVariable("guguncode") int guguncode, @PathVariable("optioncode") int optioncode) {
		try {
			logger.debug("recommend 추천 코드 : " + areacode + " ,  " + guguncode + ", " + optioncode);
			Map<String, Integer> map = new HashMap<>();
			map.put("areacode", areacode);
			map.put("guguncode", guguncode);
			map.put("optioncode", optioncode);
			List<AttractionDto> sigungus = attractionService.getAttractionsWithOption(map);
			if (sigungus != null && !sigungus.isEmpty()) {
				return new ResponseEntity<List<AttractionDto>>(sigungus, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/defaultrecommend/{lat}/{lon}")
	private ResponseEntity<?> defaultrecommend(@PathVariable("lat") double lat, @PathVariable("lon") double lon){
		try {
			Map<String, Double> map = new HashMap<>();
			map.put("lat", lat);
			map.put("lon", lon);
			List<AttractionDto> attractions = attractionService.getDefaultAttractions(map);
			
			logger.debug("기본 추천^^");
			if (attractions != null && !attractions.isEmpty()) {
				return new ResponseEntity<List<AttractionDto>>(attractions, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/gugun/{regcode}")
	private ResponseEntity<?> gugun(@PathVariable("regcode") int regcode) {
		try {
			logger.debug("gugun 코드");
			List<SigungucodeDto> sigungus = attractionService.getSigungucode(regcode);
			if (sigungus != null && !sigungus.isEmpty()) {
				return new ResponseEntity<List<SigungucodeDto>>(sigungus, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	@GetMapping("/recommend/{sidocode}/{guguncode}")
	private ResponseEntity<?> recommend(@PathVariable("sidocode") int areacode, @PathVariable("guguncode") int guguncode) {
		try {
			logger.debug("recommend 추천 코드 : " + areacode + " ,  " + guguncode);
			Map<String, Integer> map = new HashMap<>();
			map.put("areacode", areacode);
			map.put("guguncode", guguncode);
			List<AttractionDto> sigungus = attractionService.getAttractions(map);
			if (sigungus != null && !sigungus.isEmpty()) {
				return new ResponseEntity<List<AttractionDto>>(sigungus, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<?> exceptionHandling(Exception e) {
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String act = request.getParameter("act");
//		
//		if ("attraction".equals(act)) {
//			attraction(request, response);
//		} else if ("mvfacilities".equals(act)) {
//			mvfacilities(request, response);
//		} else if ("gugun".equals(act)) {
//			gugun(request, response);
//		} else if ("recommend".equals(act)) {
//			recommend(request, response);
//		} else if ("attractiondetail".equals(act)) {
//			attractiondetail(request, response);
//		} else if ("defaultrecommend".equals(act)) {
//			defaultrecommend(request, response);
//		} else if ("facilities".equals(act)) {
//			facilities(request, response);
//		} else {
//			redirect(request, response, "/main.jsp");
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		doGet(request, response);
//	}
//	
//	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
//			throws ServletException, IOException {
//		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//		dispatcher.forward(request, response);
//	}
//
//	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
//		response.sendRedirect(request.getContextPath() + path);
//	}
//	
//	///////////////////////////////////////////
//	
//	private void attraction(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			List<AreacodeDto> areas = attractionService.getAreacode();
//			request.setAttribute("areas", areas);
//			forward(request, response, "/attraction/attraction-top30.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void mvfacilities(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			List<AreacodeDto> areas = attractionService.getAreacode();
//			request.setAttribute("areas", areas);
//			forward(request, response, "/attraction/nearby-facilities.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void gugun(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			int regcode = Integer.parseInt(request.getParameter("regcode"));
//			List<SigungucodeDto> sigungus = attractionService.getSigungucode(regcode);
//			request.setAttribute("sigungus", sigungus);
//			forward(request, response, "/attraction/gungu.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void recommend(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			int areacode = Integer.parseInt(request.getParameter("areaCode"));
//			int guguncode = Integer.parseInt(request.getParameter("gugunCode"));
//			List<AttractionDto> attractions = attractionService.getAttractions(areacode, guguncode);
//			request.setAttribute("attractions", attractions);
//			forward(request, response, "/attraction/recommend.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void attractiondetail(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			int contentid = Integer.parseInt(request.getParameter("contentid"));
//			AttractionDto attraction = attractionService.getAttraction(contentid);
//			request.setAttribute("attraction", attraction);
//			forward(request, response, "/attraction/attraction-details.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void defaultrecommend(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			double lat = Double.parseDouble(request.getParameter("lat"));
//			double lon = Double.parseDouble(request.getParameter("lon"));
//			List<AttractionDto> attractions = attractionService.getDefaultAttractions(lat, lon);
//			request.setAttribute("attractions", attractions);
//			forward(request, response, "/attraction/recommend.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void facilities(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			int areacode = Integer.parseInt(request.getParameter("areaCode"));
//			int guguncode = Integer.parseInt(request.getParameter("gugunCode"));
//			int optioncode = Integer.parseInt(request.getParameter("optionCode"));
//			List<AttractionDto> attractions = attractionService.getAttractions(areacode, guguncode, optioncode);
//			request.setAttribute("attractions", attractions);
//			forward(request, response, "/attraction/recommend.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
