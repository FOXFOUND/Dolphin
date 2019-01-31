package org.facengineer.PublicTools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.facengineer.Model.Person;

public class PublicUtils {
    public static <T> T LinkedMapToObj(Object data, Class<T> _classname) {
        return new ObjectMapper().convertValue(data, _classname);
    }
}
