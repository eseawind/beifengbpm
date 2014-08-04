package com.beifengbpm.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class BeifengBPMScriptEngine {
	public static boolean findValueByCondition(String condition,Map dateMap){
		
		boolean flag = false;
		if (StringUtils.isNotEmpty(condition)) {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			Set set = dateMap.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Object key =  iterator.next();
				Object value = dateMap.get(key);
				engine.put(""+key, value);
				
			}
			Object result = null;
			try {
				result = engine.eval(condition);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
			flag = Boolean.parseBoolean(result.toString());
		}
		return flag;
	}
}
