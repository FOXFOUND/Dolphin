package org.facengineer.WebPage;

import org.facengineer.DaoMapper.FileDao;
import org.facengineer.DaoMapper.PersonModel;
import org.facengineer.Model.FileModel;
import org.facengineer.Model.Person;
import org.facengineer.PublicTools.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/list")
public class SqlInfoList {
    private final PersonModel personModel;
    private final FileDao fileDao;

    public SqlInfoList(PersonModel personModel, FileDao fileDao) {
        this.personModel = personModel;
        this.fileDao = fileDao;
    }

    @RequestMapping("/")
    public String List(Model model) {
        List<Person> personlist = this.personModel.AllPersonList();
        List<FileModel> filelist = this.fileDao.GetFileList();
        List<String> USER_COLUMNLIST = Configuration.SqlColumnsList.get("USER");
        List<String> FILE_COLUMNLIST = Configuration.SqlColumnsList.get("FILE");
        model.addAttribute("usercolumn",USER_COLUMNLIST);
        model.addAttribute("filecolumn",FILE_COLUMNLIST);
        model.addAttribute("personlist",personlist);
        model.addAttribute("filelist",filelist);

        return "SqlPage";
    }
}
