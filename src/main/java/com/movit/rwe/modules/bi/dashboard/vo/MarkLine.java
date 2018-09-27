package com.movit.rwe.modules.bi.dashboard.vo;

import java.util.List;

import net.sf.json.JSONArray;

public class MarkLine {

	private boolean animation;
	private LineStyle lineStyle;
	private List<JSONArray> data;
	private Tooltip tooltip;
	
	public Tooltip getTooltip() {
		return tooltip;
	}
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	public boolean isAnimation() {
		return animation;
	}
	public void setAnimation(boolean animation) {
		this.animation = animation;
	}
	public LineStyle getLineStyle() {
		return lineStyle;
	}
	public void setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
	}
	public List<JSONArray> getData() {
		return data;
	}
	public void setData(List<JSONArray> data) {
		this.data = data;
	}
	
}
