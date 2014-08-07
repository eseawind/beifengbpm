package com.beifengbpm.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class BeifengBPMScriptEngine {

	public static boolean findValueByCondition(String condition, Map dataMap) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(condition)) {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			Set set = dataMap.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = dataMap.get(key);
				engine.put(key + "", value);
			}
			Object result = null;
			try {
				result = engine.eval(condition);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag = Boolean.parseBoolean(result.toString());
		}
		return flag;
	}

	public static void main(String[] args) {
		Map datamap = new HashMap();
		// datamap.put("user", "aaaaaaaa");
		datamap.put("agree", "no");
		boolean flag = findValueByCondition("agree=='no'", datamap);
		System.out.println(flag);
	}
}
