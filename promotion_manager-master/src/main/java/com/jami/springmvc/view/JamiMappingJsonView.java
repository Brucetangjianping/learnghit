package com.jami.springmvc.view;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cyningsun
 * @Created 2014-4-15 <a href="sunyinhangscut@gmail.com">cyningsun</a>
 */

public class JamiMappingJsonView extends MappingJacksonJsonView {
	private String[] filterAttribute = {"openid","utoken","cid"};

    private String[] attributes = {"retCode", "errCode", "msg", "dtag", "data"};
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		Object result = super.filterModel(model);
		/*if (!(result instanceof Map)) {
		       return result;
		}
		Map map = (Map) result;
        Map newMap = new HashMap();
		for(String attr : attributes) {
            newMap.put(attr, map.get(attr));
		}
		return newMap;*/
		return result;
	}
}
