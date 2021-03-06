package org.facengineer.WebPage;

import org.facengineer.DaoMapper.FileDao;
import org.facengineer.Model.FileModel;
import org.facengineer.PublicTools.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileList {
    private FileDao fd;

    public FileList(FileDao fd) {
        this.fd = fd;
    }

    @RequestMapping(value = "/list/")
    public String FileList(Model model) {
        List<FileModel> filelist = this.fd.GetFileList();
        List<String> FILE_COLUMNLIST = Configuration.SqlColumnsList.get("FILE");
        model.addAttribute("filecolumn", FILE_COLUMNLIST);
        model.addAttribute("filelist", filelist);
        return "FileList";
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<InputStreamResource> preview(@PathVariable Integer id) throws Exception {
        FileModel fm = fd.GetNameByID(id);
        String filename = fm.getName() + fm.getSuffixname();
        FileSystemResource file = new FileSystemResource(Configuration.FILEPATH + filename);
        String FileType = Files.probeContentType(Paths.get(Configuration.FILEPATH + filename));
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-disposition", "inline; filename=\"" + fm.getFilename() + "\"");
        if(FileType!=null)
            headers.add("Content-Type",FileType);
        else
            headers.add("Content-Type","application/force-download");
        return ResponseEntity.ok().headers(headers).contentLength(file.contentLength()).body(new InputStreamResource(file.getInputStream()));
    }
}
