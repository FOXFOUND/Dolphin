package org.facengineer.WebPage;

import org.facengineer.DaoMapper.FileDao;
import org.facengineer.Model.FileModel;
import org.facengineer.PublicTools.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class Index {
    private FileDao fd;

    public Index(FileDao fd) {
        this.fd = fd;
    }

    @RequestMapping(value = "/post")
    public String Post(){
        return "post";
    }

    @RequestMapping(value = "/")
    public String Index(Model model,HttpServletRequest request) {
        List<FileModel> filelist = this.fd.GetFileList();
        List<String> FILE_COLUMNLIST = new ArrayList<>();
        for (String value : Configuration.SqlColumnsList.get("FILE")){
            FILE_COLUMNLIST.add(value);
        }
        model.addAttribute("filecolumn", FILE_COLUMNLIST);
        model.addAttribute("filelist", filelist);
        FILE_COLUMNLIST.remove("name");
        FILE_COLUMNLIST.remove("suffixname");
        FILE_COLUMNLIST.remove("url");
        return "index";
    }

    @RequestMapping("/admin")
    public String Index() {
        return "admin";
    }
}
