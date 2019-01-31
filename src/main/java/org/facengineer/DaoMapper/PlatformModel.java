package org.facengineer.DaoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlatformModel {
    @Select("Show tables")
    List<String> ShowTables();

    @Select("select COLUMN_NAME from information_schema.columns where table_name=#{tablename}")
    List<String> DescribeTable(@Param("tablename") String tablename);
}
