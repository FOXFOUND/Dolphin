package org.facengineer.Plugins;

import org.facengineer.DaoMapper.PlatformModel;
import org.facengineer.Model.FileModel;
import org.facengineer.Model.Person;
import java.util.List;

public class PageHandler extends InitialHandler {
    public PageHandler(PlatformModel _platformmodel) {
        super(_platformmodel);
    }

    public List<FileModel> GetFileList(int start_id) {
        try {
            List<FileModel> _result =
                    GetListById(PluginsConfig.FilePageHandler_SqlCmd, start_id, PluginsConfig.PageMos, FileModel.class);
            return _result;
        } catch (Exception e) {

        }
        return null;
    }

    public List<Person> GetPersonList(int start_id) {
        try {
            List<Person> _result =
                    GetListById(PluginsConfig.PersonPageHandler_SqlCmd, start_id, PluginsConfig.PageMos, Person.class);
            return _result;
        } catch (Exception e) {
        }
        return null;
    }
}
