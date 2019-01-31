package org.facengineer.DaoMapper;

import org.apache.ibatis.annotations.*;
import org.facengineer.Model.Person;

import java.util.List;

@Mapper
public interface PersonModel {

    @Select("select * from USER")
    List<Person> AllPersonList();

    @Select("select * from USER where id = #{id}")
    Person FindPersonById(@Param("id") int id);

    @Insert("insert into person(name,password,email) values(#{name},#{password},#{email})")
    public boolean InsertPersonValue(@Param("name") String name,@Param("password") String password,@Param("email") String email);

    @Select("select password from USER where name = #{name}")
    String GetPasswordByName(@Param("name") String name);

    @Insert("insert into USERTOKEN(USERNAME,TOKEN, START_TIME) values(#{username},#{token} ,#{start_time})")
    public boolean SetTokenByName(@Param("username") String username , @Param("token") String token, @Param("start_time") long start_time);

    @Insert({
            "<script>" +
                    "insert into USER (name, password,email) values " +
                    "<foreach  collection='personlist' item='dmo' separator=','>" +
                    "( #{dmo.name,jdbcType=VARCHAR}, #{dmo.password,jdbcType=INTEGER}, #{dmo.email,jdbcType=VARCHAR})",
            "</foreach>" +
                    "</script>"
    })
    public boolean insertPersonBatch(@Param("personlist") List<Person> personList);
}
