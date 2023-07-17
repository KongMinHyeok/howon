package kr.co.voard.jwt;

import java.util.Date;
/** 
 * 
 * X-AUTH-TOKEN : 사용자 인증을 위해 사용되는 헤더이름중 하나 (인증- 유효한 토큰인지 확인)
 * X-ACCESS-TOKEN : 사용자의 권한을 위해 사용되는 헤더 이름중 하나 (권한 - 엑세스 권한 제어)
 * 
 * JWT란 ? - JSON WEB TOKEN 유저를 인증하고 식별하기위한 토큰 기반 인증
 * 
 * 
 * */
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component 
public class JWTUtil {

	private final long TOKEN_VALIDATE_DAY = 1000 * 60 * 60 * 1; // 1시간
	private SecretKey secretKey;
	
	// JWT의 프로퍼티값 설정
	public JWTUtil(@Value("${jwt.secret}") String secret) { 
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // secret주입
	}
	
	// JWT에서 클레임값 추출
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) { 
		
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(secretKey)
							.build()
							.parseClaimsJws(token)
							.getBody();
		
		return claimsResolver.apply(claims);
	}	
	
	
	// JWT에서 사용자 아이디 추출
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	// JWT 유효성 검사
	public boolean validateToken(String token) {
		Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return !expiration.before(new Date());
	}
	
	// HTTP 헤더에서 Token 추출
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN"); // x-auth-token 헤더에서 토큰값을 가져옴
	}
	
	// JWT 생성
	public String generateToken(String username) {
		
		Map<String, Object> claims = new HashMap<>();
		
		Date createDate = new Date();
		Date expireDate = new Date(createDate.getTime() * TOKEN_VALIDATE_DAY);
		
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(username)
					.setIssuedAt(createDate)
					.setExpiration(expireDate)
					.signWith(secretKey, SignatureAlgorithm.HS256)
					.compact();
	}
	
}
