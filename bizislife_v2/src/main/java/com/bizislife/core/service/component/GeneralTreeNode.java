package com.bizislife.core.service.component;

import java.io.Serializable;
import java.util.List;

public class GeneralTreeNode implements TreeNode, Serializable{
	
	private static final long serialVersionUID = 7572284619004541695L;
	
	private String prettyName;
	private String systemName;
	
	private List<GeneralTreeNode> nodes;

	public String getPrettyName() {
		return prettyName;
	}

	public void setPrettyName(String prettyName) {
		this.prettyName = prettyName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public List<GeneralTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<GeneralTreeNode> nodes) {
		this.nodes = nodes;
	}

}
