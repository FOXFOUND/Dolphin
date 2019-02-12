package org.facengineer.WebRest;

import org.facengineer.PublicTools.AuthToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/token")
public class TokenTest {
    @RequestMapping(value = "/get/")
    public String GetUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String result = GetNameByToken(session);
        return result;
    }

    private String GetNameByToken(HttpSession session) {
        AuthToken token = new AuthToken();
        AuthToken.AuthPersonSignDecrypt decrypt_token = new AuthToken.AuthPersonSignDecrypt((String) session.getAttribute("JSESSION"));
        return decrypt_token.GetUserName();
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", GetUserName(request));
        return map;
    }
}
