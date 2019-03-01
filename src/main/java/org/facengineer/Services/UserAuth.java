package org.facengineer.Services;

import org.facengineer.PublicTools.AuthToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserAuth {
    public static String GetUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String result = GetNameByToken(session);
        return result;
    }

    public static String GetNameByToken(HttpSession session) {
        AuthToken token = new AuthToken();
        AuthToken.AuthPersonSignDecrypt decrypt_token = new AuthToken.AuthPersonSignDecrypt((String) session.getAttribute("JSESSION"));
        return decrypt_token.GetUserName();
    }
}
