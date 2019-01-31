package org.facengineer.WebPage;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import org.facengineer.DaoMapper.FileDao;
import org.facengineer.Model.FileModel;
import org.facengineer.PublicTools.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/upload")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUpload {
    private FileDao fd;

    public FileUpload(FileDao fd) {
        this.fd = fd;
    }

    @RequestMapping(value = "/file")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Gson gson = new Gson();
        if (file.isEmpty()) {
            return "文件为空";
        }

        String UploadFileName = UUID.randomUUID().toString();
        if (UploadFileName.equals("")) {
            return "上传失败";
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
//        String caselsh = fileName.substring(0,fileName.lastIndexOf("."));
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;

        File dest = new File(Configuration.FILEPATH + UploadFileName + suffixName);

        try {
            file.transferTo(dest);
            FileModel filemodel = new FileModel();
            filemodel.setName(UploadFileName + suffixName);
            filemodel.setUrl(Configuration.FILEPATH);
            filemodel.setFilename(fileName);
            filemodel.setSuffixname(suffixName);
            fd.InsertFileValue(UploadFileName,suffixName,fileName,"http://localhost:8080/file/","tang");
            return gson.toJson(filemodel);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

}
