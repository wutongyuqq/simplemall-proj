
package com.simplemall.micro.serv.page.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.simplemall.micro.serv.common.util.UUIDUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * ClassName: JWTUtils <br/>
 * Function:Json Web Token的java实现<br/>
 * date: 2018年7月9日 下午2:26:01 <br/>
 *
 * @author fanyoucai
 * @version
 * @since JDK 1.6
 */
public class JWTUtils {
	
	static String SECRETKEY = "OVlpXYjNwaFJYUllVbXhXTkZaR1pEQlNiVkYzWTBac1YxWkZXbE";

	/**
	 * 由字符串生成加密key
	 *
	 * @return
	 */
	public static SecretKey generalKey(String stringKey) {
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/**
	 * createJWT: 创建jwt<br/>
	 *
	 * @author fanyoucai
	 * @param id
	 *            唯一id，uuid即可
	 * @param subject
	 *            json形式字符串或字符串，增加用户非敏感信息存储，如user tid，与token解析后进行对比，防止乱用
	 * @param ttlMillis
	 *            有效期
	 * @param stringKey
	 * @return jwt token
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey(SECRETKEY);
		Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
		claims.put("uid", "DSSFAWDWADAS...");
		claims.put("user_name", "admin");
		claims.put("nick_name","DASDA121");
		JwtBuilder builder = Jwts.builder().setClaims(claims).setIssuer("").setId(id).setIssuedAt(now).setSubject(subject)
				.signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * parseJWT: 解密jwt <br/>
	 *
	 * @author fanyoucai
	 * @param jwt
	 * @param stringKey
	 * @return
	 * @throws ExpiredJwtException
	 * @throws UnsupportedJwtException
	 * @throws MalformedJwtException
	 * @throws SignatureException
	 * @throws IllegalArgumentException
	 * @since JDK 1.6
	 */
	public static Claims parseJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException {
		SecretKey key = generalKey(SECRETKEY);
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
		return claims;
	}

	public static void main(String[] args) {
		try {
			String token = createJWT(UUIDUtils.getUUID(), "", 1000);
			System.out.println(token);
			token="eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJ1c2VyX25hbWUiOiJhZG1pbiIsIm5pY2tfbmFtZSI6IkRBU0RBMTIxIiwiaXNzIjoiIiwiZXhwIjoxNTMxMTE3MDY0LCJpYXQiOjE1MzExMTcwNjMsImp0aSI6ImI4ODgwNWNkNzlkZDRjOGJhMTUwY2Q2OWIyMGY3MzVhIn0.thB7HxeLKBsLDW9gAqkx2jRwGLdZkOdRGNw14Gif_D0";
			Claims claims = parseJWT(token);
			System.out.println(claims.getId()+"##"+claims.getExpiration()+"///"+claims.getExpiration().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
