package org.facengineer.PublicTools;

import org.facengineer.DaoMapper.PlatformModel;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebConfigListener implements ServletContextListener {

    private PlatformModel platformModel;

    public WebConfigListener(PlatformModel platformModel){
        this.platformModel = platformModel;
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        org.facengineer.PublicTools.Configuration.SqlColumnsList = GenerateSqlColumnsList();
    }

    private Map<String,List<String>> GenerateSqlColumnsList(){
        Map<String,List<String>> Result = new HashMap<>();
        for(String TableName : this.platformModel.ShowTables()){
            Result.put(TableName.toUpperCase(),this.platformModel.DescribeTable(TableName));
        }
        return Result;
    }
}
