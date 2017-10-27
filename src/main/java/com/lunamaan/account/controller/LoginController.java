package com.lunamaan.account.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.lunamaan.account.domain.Member;
import com.lunamaan.account.model.MemberSession;
import com.lunamaan.account.model.Naver;
import com.lunamaan.account.model.SocialType;
import com.lunamaan.account.repository.MemberRepository;


@Controller
public class LoginController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;

	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private Naver naverConnectionFactory;

	
	public LoginController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }
	
	@RequestMapping("/login")
	public String singin(Model model, HttpSession session, @RequestParam(value="returnUrl", defaultValue="") String returnUrl){
		MemberSession memberSession = (MemberSession) session.getAttribute("memberSession");

		if(memberSession == null){
			memberSession = new MemberSession();
			memberSession.setLogin(false);
		}
		
		model.addAttribute("account", memberSession);
		

	    SecureRandom random = new SecureRandom();
	    String state = new BigInteger(130, random).toString();	    
	    session.setAttribute("state", state);
	    
		model.addAttribute("apiURL", naverConnectionFactory.getLoginUrl(state));
		model.addAttribute("returnUrl", returnUrl);
		    
		return "account/login";
	}
	
	
	@RequestMapping("/login/google")
	public String ggSingin(Model model, HttpSession session){
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		
		return "redirect:"+url;
	}
	
	@RequestMapping("/login/facebook")
	public String fbSingin(Model model, HttpSession session){
		return "account/login_fb";
	}
	
	
	
	@RequestMapping(value = "/login/{socialDomain}/callback", method = RequestMethod.GET)
	public String loginCallback(Model model, HttpSession session,
			@PathVariable(value = "socialDomain", required = true) String socialDomain,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "state", required = false) String state,
			HttpServletRequest request, HttpServletResponse response){
	 
		SocialType socialType = SocialType.getSocialType(socialDomain);
		model.addAttribute("error", error);
		
		String socialId = "";
		if(socialType== SocialType.FACEBOOK){
			socialId = fbCallback();
		}else if(socialType == SocialType.GOOGLE){
			socialId = ggCalback(code);
		}else if(socialType == SocialType.NAVER){
			socialId = naverCalback(state, code);
		}
		
		if(!StringUtils.isEmpty(error)){
			model.addAttribute("rtnType", "error");
			model.addAttribute("msg", error);
			return "account/login_pr";
		}
		
		MemberSession memberSession = (MemberSession) session.getAttribute("memberSession");
		if(memberSession == null){
			memberSession = new MemberSession();			
		}
		
		memberSession.setSocialType(socialType);
		memberSession.setLogin(true);
		session.setAttribute("accountSession", memberSession);
		memberSession = (MemberSession) session.getAttribute("memberSession");
		
		model.addAttribute("account", memberSession);
		return "account/login_pr";
	}
				  
	
	@RequestMapping("/logout")
	@ResponseBody
	public void singout(Model model, HttpSession session){
	    session.setAttribute("account", null);
	}
	
	private String fbCallback(){
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "";
		}
	
		String [] fields = { "id", "email", "name" }; //Depends on which field you want.
		User userProfile = facebook.fetchObject("me", User.class, fields);
		return userProfile.getId();		
	}
	
	private String ggCalback(String code){
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations(); 
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(), null); 
		String accessToken = accessGrant.getAccessToken(); Long expireTime = accessGrant.getExpireTime(); 
		if (expireTime != null && expireTime < System.currentTimeMillis()) { 
			accessToken = accessGrant.getRefreshToken(); 
		} 
		Connection<Google>connection = googleConnectionFactory.createConnection(accessGrant); 
		Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi(); 
		PlusOperations plusOperations = google.plusOperations(); 
		Person person = plusOperations.getGoogleProfile();
		
		return person.getId();
	}
	
	private String naverCalback(String state, String code){
	 	ResponseEntity<String> response = restTemplate.getForEntity(naverConnectionFactory.getTokenUrl(state, code), String.class);
		// 응답코드 확인
	 
		String responseStr = response.getBody();
		JSONObject jsonObj = getJsonObject(responseStr);
		String token = (String) jsonObj.get("access_token");
		
		
		if(token.isEmpty()){
			return "";			
		}
		
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		HttpHeaders headers = new HttpHeaders();		  
		headers.set("Authorization", header);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		response = restTemplate.postForEntity(naverConnectionFactory.getProfileUrl(), entity, String.class);
		
		responseStr = response.getBody();
		jsonObj = getJsonObject(responseStr);
		jsonObj = (JSONObject) jsonObj.get("response");
		
		return (String) jsonObj.get("id");
	}

	private JSONObject getJsonObject(String responseStr) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject)jsonParser.parse(responseStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return jsonObj;
	}
	
}

