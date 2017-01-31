package com.co.levelmoney.util;

import java.util.Map;

import com.co.levelmoney.test.domain.Summary;

public class JSONUtil {

	/**
	 * 
	 * @param monthlyMap
	 * @return
	 */
	public String convertMaptoJSON(Map<String, Summary> monthlyMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(Map.Entry<String, Summary> entry : monthlyMap.entrySet())
		{
			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append(entry.getValue().toString());
			sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}

}
