package org.facengineer.WebRest;

import org.facengineer.DaoMapper.FileDao;
import org.facengineer.DaoMapper.PersonModel;
import org.facengineer.Model.FileModel;
import org.facengineer.Model.Person;
import org.facengineer.PublicTools.BaseResponse;
import org.facengineer.PublicTools.RespCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/db")
public class DBTest {
    private PersonModel personModel;
    private FileDao fd;

    public DBTest(PersonModel personModel, FileDao fd) {
        this.personModel = personModel;
        this.fd = fd;
    }

    @RequestMapping("/person/")
    public String ForeachInsertValue() {
        List<Person> personlist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person();
            person.setName("" + i);
            person.setPassword("" + i);
            person.setEmail("" + i);
            personlist.add(person);
        }
        personModel.insertPersonBatch(personlist);
        return "TRUE";
    }

    @RequestMapping("/foto/{start_id}/")
    public BaseResponse Foto(@PathVariable int start_id){
        List<FileModel> filelist = fd.GetFotoListByPosition(start_id);
        return new BaseResponse(RespCode.SUCCESS,filelist);
    }

    @RequestMapping("/fuzzy/{suffixname}/")
    public BaseResponse Fuzzy_GetFileListBySuffixName(@PathVariable String suffixname) {
        List<FileModel> filelist = fd.Fuzzy_GetFileListBySuffixName("%" + suffixname);
        return new BaseResponse(RespCode.SUCCESS,filelist);
    }
}
