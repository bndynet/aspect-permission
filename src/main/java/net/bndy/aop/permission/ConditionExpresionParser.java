package net.bndy.aop.permission;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionExpresionParser {
	private final static String PATTERN_GROUP= "\\([^(]+?\\)";
	
	private String conditionString;
	private Map<String, ConditionExpression> map = new HashMap<>();
	private Map<String, String> expressions = new HashMap<>();
	private Pattern pattern;
	private int tempLevel = 0;
	
    public ConditionExpresionParser(String conditionString) {
    	this.conditionString = conditionString;
    	this.pattern = Pattern.compile(PATTERN_GROUP);
	}
    
    public void result() {
    	this.findGroup(this.conditionString);
	}
    
    public void findGroup(String text) {
    	Matcher matcher = this.pattern.matcher(text);
    	int idx = 0;
    	String replacedResult = text;
    	while (matcher.find()) {
    		String key = "${" + this.tempLevel + "-" + idx + "}";
    		String value = matcher.group();
    		this.expressions.put(key, value);
    		replacedResult = replacedResult.replace(value, key);
    		idx++;
		}
    	this.tempLevel++;
		System.out.println(replacedResult);
		if (replacedResult.indexOf("(") >= 0 && replacedResult.indexOf(")") > 0) {
			findGroup(replacedResult);
		}
	}
    
//    private ConditionExpression toExpression(String conditionUnit) {
//    	conditionUnit = conditionUnit.replaceAll("[\\(\\)]", "");
//    	for(String v: conditionUnit.split("(and)|(or)")) {
//    		
//    	}
//    }
}
