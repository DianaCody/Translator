package org.samsu.youdaofanyi.model;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

public class ResultModel {
	
	private final Element fanyiElement;
	private Element errorCodeElement;
	private Element basicElement;
	private Element phoneticElement;//音标
	private Element explainsElement;//释义
	
	private String errorCode;
	private String phonetic;
	private List<String> explainsList = new ArrayList<String>();;
	
	public ResultModel(Document doc) {
		Element rootElement = doc.getRootElement();
		fanyiElement = rootElement;
		errorCodeElement = fanyiElement.getChild("errorCode");
		//errorCode
		errorCode = errorCodeElement.getValue();
		basicElement = fanyiElement.getChild("basic");
		if(basicElement != null){
			phoneticElement = basicElement.getChild("phonetic");
			explainsElement = basicElement.getChild("explains");
		}
		//phonetic
		if(phoneticElement != null){
			phonetic = phoneticElement.getValue();
		}
		List<Element> exElementList = null;
		if(explainsElement != null){
			exElementList = explainsElement.getChildren("ex");
		}
		if(exElementList != null){
			for (Element exElement : exElementList) {
				String exValue = exElement.getValue();
				explainsList.add(exValue);
			}
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getPhonetic() {
		if(phonetic == null || phonetic.isEmpty()){
			return "";
		}
		return phonetic;
	}

	public List<String> getExplains() {
		return explainsList;
	}
	
	public static String getFormattedDisplatString(ResultModel rm){
		String separator = System.getProperty("line.separator");
		if(rm.getExplains().size()==0){
			return "暂无翻译结果";
		}else{
			StringBuilder sb = new StringBuilder();
			if(!rm.getPhonetic().isEmpty()){
				sb.append("音标: [" + rm.getPhonetic() + "]").append(separator);
			}
			sb.append("释义:").append(separator);
			for (int i = 0; i < rm.getExplains().size(); i++) {
				String str = rm.getExplains().get(i);
				sb.append(str).append(separator);
			}
			return sb.toString();
		}
	}
	
}
