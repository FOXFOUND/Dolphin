package org.facengineer.WebRest;

import org.facengineer.DaoMapper.FileDao;
import org.facengineer.DaoMapper.PersonModel;
import org.facengineer.DaoMapper.PlatformModel;
import org.facengineer.Model.FileModel;
import org.facengineer.Model.Person;
import org.facengineer.Plugins.PageHandler;
import org.facengineer.PublicTools.BaseResponse;
import org.facengineer.PublicTools.RespCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/db")
public class DBTest {
    private PersonModel personModel;
    private FileDao fd;
    private PlatformModel platformModel;
    private PageHandler _handler;

    public DBTest(PersonModel personModel, FileDao fd, PlatformModel platformModel) {
        this.personModel = personModel;
        this.fd = fd;
        this.platformModel = platformModel;
        this._handler = new PageHandler(platformModel);
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

    @RequestMapping("/file/{start_id}/")
    public BaseResponse file(@PathVariable int start_id){
        List<FileModel> filemap = _handler.GetFileList(start_id);
        return new BaseResponse(RespCode.SUCCESS,filemap);
    }

    @RequestMapping("/person/{start_id}/")
    public BaseResponse person(@PathVariable int start_id){
        List<Person> _result = _handler.GetPersonList(start_id);
        return new BaseResponse(RespCode.SUCCESS,_result);
    }

    @RequestMapping("/fuzzy/{suffixname}/")
    public BaseResponse Fuzzy_GetFileListBySuffixName(@PathVariable String suffixname) {
        List<FileModel> filelist = fd.Fuzzy_GetFileListBySuffixName("%" + suffixname);
        return new BaseResponse(RespCode.SUCCESS,filelist);
    }
}
