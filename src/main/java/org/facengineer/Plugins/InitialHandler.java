package org.facengineer.Plugins;

import org.facengineer.DaoMapper.PlatformModel;
import org.facengineer.PublicTools.PublicUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InitialHandler<T> {
    private int user_data;

    public PlatformModel _platformmodel;

    public InitialHandler(PlatformModel _platformmodel) {
        this._platformmodel = _platformmodel;
    }

    public List<Object> GetListById(String SQL_COMMAND, int start_id, int mos,Class type) throws Exception{
        List<Map<String, Object>> sql_result = _platformmodel.GetListById(SQL_COMMAND, start_id, mos);
        List<Object> ResultList = new LinkedList<>();
        for (int i = 0; i < sql_result.size(); i++) {
            Map<String, Object> ResultMap = new HashMap<>();
            for (String k : sql_result.get(i).keySet()) {
                ResultMap.put(k, sql_result.get(i).get(k));
            }
            ResultList.add(PublicUtils.convertMap(type,ResultMap));
        }
        return ResultList;
    }
}
