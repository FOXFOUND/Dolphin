package org.facengineer.WebRest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import org.facengineer.DaoMapper.PersonModel;
import org.facengineer.Model.Person;
import org.facengineer.PublicTools.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Login {
    private final PersonModel personModel;

    public Login(PersonModel personModel) {
        this.personModel = personModel;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse PersonLoginForm(@RequestParam(value = "name", required = true) String name,
                                        @RequestParam(value = "password", required = true) String pwd, HttpServletRequest http_request) {

        return LoginCheck(name, pwd,http_request);
    }

    @RequestMapping(value = "/jlogin")
    public BaseResponse PersonLoginJson(@RequestBody BaseRequest request, HttpServletRequest http_request) {
        String JsonValue = new Gson().toJson(request);
        Person person = (Person) PublicUtils.LinkedMapToObj(request.getData(), Person.class);
        if (null != person) {
            return LoginCheck(person.getName(), person.getPassword(),http_request);
        } else
            return BaseResponse.ErrorPage();
    }

    private BaseResponse LoginCheck(String username, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Person person = new Person();
        BaseResponse response;
        if (password.equals(this.personModel.GetPasswordByName(username))) {
            person.setName(username);
            AuthToken AT = new AuthToken();
            String token = AT.AuthPersonSignEncrypt(username);
            if (SetTokenByName(username, token, request,person))
                response = new BaseResponse(RespCode.SUCCESS, person);
            else
                response = new BaseResponse(RespCode.SUCCESS, "Token Import ERROR");
        } else {
            response = new BaseResponse(RespCode.SUCCESS, "Password ERROR");
        }
        return response;
    }

    private boolean SetTokenByName(String username, String token, HttpServletRequest request, Person person) {
        HttpSession _session = request.getSession();
        person.setToken(token);
        _session.setAttribute("JSESSION",token);
        return true;
    }
}
