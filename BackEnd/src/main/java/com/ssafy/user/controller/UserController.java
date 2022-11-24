package com.ssafy.user.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.JwtServiceImpl;
import com.ssafy.user.model.service.UserService;

//@WebServlet("/user")
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	private UserService userService;
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserService userService) {
		logger.info("UserController 생성 완료");
		this.userService = userService;
	}

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private ServletContext servletContext;

	@GetMapping("/regist")
	public ModelAndView join() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/register");
		return mav;
	}

	@PostMapping("/regist")
	public ResponseEntity<?> join(@RequestBody UserDto userDto) throws Exception {
		logger.debug("Vue : 여기 왔는지 확인");
		try {
			userService.regist(userDto);
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/login");
		return mav;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map, HttpServletResponse response) {
		logger.debug("Vue : 여기 왔는지 확인");
		logger.debug("userid : {}", map.get("userId"));

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> user = new HashMap<>();
		user.put("userid", map.get("userId"));
		user.put("userpwd", map.get("userPwd"));
		HttpStatus status = null;
		try {
			UserDto loginUser = userService.login(user);
			if (loginUser != null) {
//				// 쿠키
//				Cookie cookie = new Cookie("ssafy_id", map.get("userId"));
//				if("checked".equals(map.get("save_status"))) {
//					logger.debug("확인점요~ " + map.get("userId"));
//					cookie.setMaxAge(60*60*24*365*40);
//				} else {
//					cookie.setMaxAge(0);
//				}
//				response.addCookie(cookie);

				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserId());// key, data
				userService.saveRefreshToken(map.get("userId"), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
//		ModelAndView mav = new ModelAndView();
//		try {
//			UserDto userDto = userService.login(map);
//			
//			logger.debug("userDto : {}", userDto);
//			if(userDto != null) {
//				session.setAttribute("userInfo", userDto);
//				
//				Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
//				/* cookie.setPath("/board"); */
//				logger.debug("오케이 인가요" + map.get("saveid"));
//				if("ok".equals(map.get("saveid"))) {
//					logger.debug("확인점요~ " + map.get("userid"));
//					cookie.setMaxAge(60*60*24*365*40);
//				} else {
//					cookie.setMaxAge(0);
//				}
//				response.addCookie(cookie);
//				mav.setViewName("redirect:/");
//				return mav;
//			} else {
//				model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
//				mav.setViewName("user/login");
//				return mav;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg", "로그인 중 문제 발생!!!");
//			mav.setViewName("error/error");
//			return mav;
//		}
	}

	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				logger.debug("로그인 아이디 : " + userid);
				UserDto userDto = userService.userInfo(userid);
				logger.debug("로그인 정보 뿌리기 : " + userDto);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userid", userDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/list/{userid}")
	public ResponseEntity<?> userList(@PathVariable("userid") String userId, HttpSession session) {
		logger.debug("SDfasdfasdf= " + userId);

		logger.debug("session id = " + session.getAttribute("userInfo"));
		try {
			UserDto userDto = userService.getMember(userId);
			if (userDto != null) {
				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
//				return new ResponseEntity<List<Void>>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/{userid}")
	@ResponseBody
	public String idCheck(@PathVariable("userid") String userId) throws Exception {
		logger.debug("idCheck userid : {}", userId);
		int cnt = userService.idCheck(userId);
		logger.debug(" 제에발 : {}", cnt);

		return cnt + "";
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String userId, HttpSession session) throws Exception {
		logger.debug("userDelete userid : {}", userId);

		try {
			userService.delete(userId);
			session.invalidate();
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/infoprofile/{userid}")
	public ResponseEntity<?> mvinfoprofile(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto userDto = userService.getMember(userid);
			logger.debug("유저 정보 받아왔음 : " + userDto);
			resultMap.put("userInfo", userDto);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("정보 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/editprofile/{userid}")
	public ResponseEntity<?> mveditprofile(@PathVariable("userid") String userid) {
		logger.debug("정보 수정으로 옴? ");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto userDto = userService.getMember(userid);
			resultMap.put("userInfo", userDto);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("정보 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

//	@GetMapping("mvinfoprofile")
//	public ModelAndView profile() {
//		ModelAndView mav = new ModelAndView();
//		
//		mav.setViewName("user/info-profile");
//		return mav;
//	}
//	
//	@GetMapping("mveditprofile")
//	public ModelAndView editprofile() {
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("user/edit-profile");
//		return mav;
//	}

	@PutMapping("/edit")
	public ResponseEntity<?> edit(@Value("${file.path.upload-files}") String filePath,
			@RequestPart(value = "userDto") UserDto user, @RequestPart(value = "profileImg", required = false) MultipartFile file)
			throws Exception {
		logger.debug("에반데? : " + user.getProfileImg());
		if (user.getProfileImg() != null && file != null) {
			String baseFile = filePath + user.getSaveFolder() + File.separator + user.getProfileImg();
			logger.debug(baseFile);
			File deleteFile = new File(baseFile);
			deleteFile.delete();
		}
		logger.debug("edit userid : {}", user);

		try {

			if (file != null) { // 프로필 사진 변경
				String realPath = servletContext.getRealPath("/upload");
//          String realPath = servletContext.getRealPath("/resources/img");
//			String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = filePath + user.getUserName();
				logger.debug("저장 폴더 : {}", saveFolder);
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();

				String originalFileName = file.getOriginalFilename();
				logger.debug("저장하는 이름 : " + saveFolder + " / " + originalFileName);
				if (!originalFileName.isEmpty()) {
//				user.setSaveFolder(today + File.separator + user.getUserId());
					user.setSaveFolder(user.getUserName());
					user.setProfileImg(originalFileName);

					file.transferTo(new File(folder, originalFileName));
				}
			}

			userService.editProfile(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/find")
	public ResponseEntity<?> find(@RequestBody Map<String, String> map) throws Exception {
		logger.debug("find userid : {}", map.get("userId"));
		logger.debug("find username : {}", map.get("userName"));

		try {
			String text = "비밀번호 = " + userService.find(map);
			return new ResponseEntity<String>(text, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
