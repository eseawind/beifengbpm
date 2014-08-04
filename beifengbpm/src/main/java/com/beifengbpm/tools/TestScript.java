package com.beifengbpm.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.collections.map.HashedMap;

public class TestScript {
	public static void main(String[] args)throws Exception {
		String str = "day<=3";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Map map = new HashedMap();
		map.put("day", ""+2);
		map.put("user", "aaaaaaaaaaaaaa");
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = map.get(key);
			engine.put(""+key, value);
		}
		Object result = engine.eval(str);
		System.out.println(result.toString());
		System.out.println(Boolean.parseBoolean(result.toString()));
	}

}
