package net.bndy.aop.permission;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionExpresionParser {
	private final static String PATTERN_GROUP= "\\([^(]+?\\)";
	
	private String conditionString;
	private Map<String, ConditionExpression> mapExpressions = new HashMap<>();
	private Map<String, String> mapValues = new HashMap<>();
	private Pattern pattern;
	private int tempLevel = 0;
	
    public ConditionExpresionParser(String conditionString) throws PermissionExpressionParseError {
    	if (conditionString.replaceAll("\\(", "").length() != conditionString.replaceAll("\\)", "").length()) {
    		throw new PermissionExpressionParseError(conditionString);
    	}

    	this.conditionString = conditionString;
    	this.pattern = Pattern.compile(PATTERN_GROUP);
	}
    
    public ConditionExpression result() throws PermissionExpressionParseError {
    	String oneValue = this.toOneValue(this.conditionString);

    	System.out.println("Values:");
    	for(String key: mapValues.keySet()) {
    		System.out.println(key + ":" + mapValues.get(key));
    	}
    	
    	System.out.println("Expressions:");
    	for(String key: mapExpressions.keySet()) {
    		System.out.println(key + ":" + mapExpressions.get(key).toString());
    	}
    	
    	ConditionExpression cExpression = this.toExpression(oneValue);
    	System.out.println(cExpression.toString());
    	
    	return cExpression;
	}
    
    public String toOneValue(String text) throws PermissionExpressionParseError {
    	String result = text;
    	Matcher matcher = this.pattern.matcher(text);
    	int idx = 0;
    	while (matcher.find()) {
    		String key = "${" + this.tempLevel + "-" + idx + "}";
    		String value = matcher.group();
    		result = result.replace(value, key);
    		value = value.replaceAll("[\\(\\)]", "");
    		this.mapValues.put(key, value);
    		this.mapExpressions.put(key, this.toExpression(value));
    		idx++;
		}
    	this.tempLevel++;
		System.out.println(result);
		if (result.indexOf("(") >= 0 && result.indexOf(")") > 0) {
			result = toOneValue(result);
		}
		return result;
	}
    
    private ConditionExpression toExpression(String conditionUnit) throws PermissionExpressionParseError {
    	String[] tmp = conditionUnit.split(" ");
    	
    	if (tmp.length == 1) {
    		return conditionUnit.startsWith("!") ? new NotConditionExpression(conditionUnit.substring(1)) : new ConditionExpression(conditionUnit);
    	}

		ConditionExpression result = null;
    	int i = 0;
    	while (i < tmp.length) {
    		String word = tmp[i];
    		// case: ${0-0} 
    		if (word.startsWith("${")) {
    			if (result != null){ 
    				throw new PermissionExpressionParseError(conditionUnit);
    			}
    			result = this.mapExpressions.get(word);
    			i++;
    			continue;
    		}
    		// case: !PCODE
    		if (word.startsWith("!")) {
    			if (result != null){
    				throw new PermissionExpressionParseError(conditionUnit);
    			}
    			result = new NotConditionExpression(word.substring(1));
    			i++;
				continue;
    		} 
    		// case: PCODE and PCODE1
			if ("and".equals(word)) {
				 if (tmp[i+1].startsWith("!")) {
					 result = result == null 
							 ? new ConditionExpression(ConditionOperation.AND, tmp[i-1],  new NotConditionExpression(tmp[i+1].substring(1)))
							 : new ConditionExpression(ConditionOperation.AND, result, new NotConditionExpression(tmp[i+1].substring(1)));
					 
				 } else if (tmp[i+1].startsWith("${")) {
					 result = result == null 
							 ? new ConditionExpression(ConditionOperation.AND, tmp[i-1],  this.mapExpressions.get(tmp[i+1]))
							 : new ConditionExpression(ConditionOperation.AND, result, this.mapExpressions.get(tmp[i+1]));
					 
				 } else {
					 result = result == null 
							 ? new AndConditionExpression(tmp[i-1], tmp[i+1]) 
							 : new ConditionExpression(ConditionOperation.AND, result, tmp[i+1]);
				 }
				i = i + 2;
				continue;
			} 
			// case: PCODE or PCODE1
			if ("or".equals(word)) {
				 if (tmp[i+1].startsWith("!")) {
					 result = result == null 
							 ? new ConditionExpression(ConditionOperation.OR, tmp[i-1],  new NotConditionExpression(tmp[i+1].substring(1)))
							 : new ConditionExpression(ConditionOperation.OR, result, new NotConditionExpression(tmp[i+1].substring(1)));
				 } else if (tmp[i+1].startsWith("${")) {
					 result = result == null 
							 ? new ConditionExpression(ConditionOperation.OR, tmp[i-1],  this.mapExpressions.get(tmp[i+1]))
							 : new ConditionExpression(ConditionOperation.OR, result, this.mapExpressions.get(tmp[i+1]));
					 
				 } else {
					 result = result == null 
							 ? new OrConditionExpression(tmp[i-1], tmp[i+1]) 
							 : new ConditionExpression(ConditionOperation.OR, result, tmp[i+1]);
				 }
				i = i + 2;
				continue;
			}
			i++;
		}
    	
    	return result;
	}
    
//    private ConditionExpression toExpression(String conditionUnit) {
//    	conditionUnit = conditionUnit.replaceAll("[\\(\\)]", "");
//    	for(String v: conditionUnit.split("(and)|(or)")) {
//    		
//    	}
//    }
}
