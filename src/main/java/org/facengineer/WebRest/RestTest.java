package org.facengineer.WebRest;

import org.apache.commons.io.FilenameUtils;
import org.facengineer.DaoMapper.FileDao;
import org.facengineer.DaoMapper.PersonModel;
import org.facengineer.Model.FileModel;
import org.facengineer.Model.Person;
import org.facengineer.Model.ResourceRequestModel;
import org.facengineer.PublicTools.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class RestTest {
    private FileDao fd;

    public RestTest(FileDao fd) {
        this.fd = fd;
    }

    @RequestMapping(value = "/jlist/")
    public BaseResponse FileList() {
        List<FileModel> filelist = this.fd.GetFileList();
        List<String> FILE_COLUMNLIST = Configuration.SqlColumnsList.get("FILE");
        BaseResponse response;
        Map<String, Object> ResultMap = new HashMap<>();
        ResultMap.put("ColumnList", FILE_COLUMNLIST);
        ResultMap.put("FileList", filelist);
        response = new BaseResponse(RespCode.SUCCESS, ResultMap);
        return response;
    }

    @RequestMapping(value = "/jget/")
    public ResponseEntity<InputStreamResource> GetResourceByJson(@RequestBody BaseRequest json_resource_request) throws Exception {
        ResourceRequestModel ResourceModel = (ResourceRequestModel) PublicUtils.LinkedMapToObj(json_resource_request.getData(), ResourceRequestModel.class);
        URL url = new URL(ResourceModel.getFileUrl());
        FileSystemResource file = new FileSystemResource(
                Configuration.FILEPATH + FilenameUtils.getName(url.getPath()));
        String FileType = Files.probeContentType(Paths.get(Configuration.FILEPATH + FilenameUtils.getName(url.getPath())));
        HttpHeaders headers = new HttpHeaders();
        if (FileType != null)
            headers.add("Content-Type", FileType);
        else
            headers.add("Content-Type", "application/force-download");
        return ResponseEntity.ok().headers(headers).contentLength(file.contentLength()).body(new InputStreamResource(file.getInputStream()));
    }
}