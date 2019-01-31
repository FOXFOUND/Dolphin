package org.facengineer.PublicTools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.facengineer.Services.TimeService;

import java.util.Date;

public class AuthToken {

    private static long TimeStamp;
    public static String AuthPersonSignEncrypt(String username) {
        TimeStamp = TimeService.GetTime();
        try {
            Date date = new Date(TimeStamp + Configuration.EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(Configuration.TOKEN_SECRET);
            return JWT.create().withClaim("USERNAME", username).withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "AuthSignEncrypt ERROR";
        }
    }

    public long GetTimeStamp(){
        return TimeStamp;
    }

    static class AuthPersonSignDecrypt {
        private DecodedJWT jwt = null;

        public DecodedJWT DeToken(final String token) {

            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Configuration.TOKEN_SECRET)).build();
                jwt = verifier.verify(token);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return jwt;
        }

        public AuthPersonSignDecrypt(String Token) {
            DecodedJWT detoken = DeToken(Token);
        }

        public String GetUserName() {
            return jwt.getClaim("USERNAME").asString();
        }

        public Date GetExpireTime() {
            return jwt.getExpiresAt();
        }
    }

//    public static void main(String[] args) {
//        AuthToken token = new AuthToken();
//        String TokenString = token.AuthPersonSignEncrypt("Tang");
//        System.out.println(TokenString);
//        AuthPersonSignDecrypt decrypt_token = new AuthPersonSignDecrypt(TokenString);
//        System.out.println(decrypt_token.GetUserName());
//        System.out.println(decrypt_token.GetExpireTime());
//    }
}
