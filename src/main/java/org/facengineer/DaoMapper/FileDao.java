package org.facengineer.DaoMapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.facengineer.Model.FileModel;

import java.util.List;

@Mapper
public interface FileDao {
    @Select("select * from FILE")
    List<FileModel> GetFileList();

    @Select("Select Max(id) from FILE")
    Integer GetMaxID();

    @Insert("insert into FILE values(0,#{name},#{suffixname},#{filename},#{url},#{uploader_name})")
    public boolean InsertFileValue(@Param("name") String FileName, @Param("suffixname") String SuffixName,@Param("filename") String filename, @Param("url") String FileUrl, @Param("uploader_name") String uploader_name);

    @Select("Select id,name,filename,url from FILE where uploader_name = #{uploadername}")
    List<FileModel> GetFileListByUser(@Param("uploadername") String uploader_name);

    @Select("SELECT name,suffixname,filename FROM Dolphin.FILE where id = #{id}")
    FileModel GetNameByID(@Param("id") Integer id);
}
