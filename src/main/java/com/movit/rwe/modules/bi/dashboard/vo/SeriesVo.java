package com.movit.rwe.modules.bi.dashboard.vo;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SeriesVo {

	private String name;
	private String type;
	private String symbol;
	private ItemStyle itemStyle;
	private Object[][] data;
	private MarkLine markLine;
	private int symbolSize=10;

	public void setData(Object[][] data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public ItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}

	public Object[][] getData() {
		return data;
	}

	public MarkLine getMarkLine() {
		return markLine;
	}

	public void setMarkLine(MarkLine markLine) {
		this.markLine = markLine;
	}

	public int getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(int symbolSize) {
		this.symbolSize = symbolSize;
	}

	public static void main(String[] args) {
		SeriesVo vo = new SeriesVo();
		vo.setName("p1_start");
		vo.setType("scatter");
		vo.setSymbol("path://d='M165.439448 62.597687 943.022199 511.536442 165.439448 960.473151Z'");

		ItemStyle itemStyle = new ItemStyle();
		JSONObject jsonNormal = new JSONObject();
		jsonNormal.put("color", "#6B9494");
		itemStyle.setNormal(jsonNormal);
		vo.setItemStyle(itemStyle);

		Integer[][] point = { { 0, 1 } };
		vo.setData(point);

		SeriesVo vo2 = new SeriesVo();
		vo2.setName("p1_ongoing");
		vo2.setType("scatter");

		ItemStyle itemStyle2 = new ItemStyle();
		JSONObject jsonNormal2 = new JSONObject();
		jsonNormal2.put("color", "#FD026C");
		itemStyle2.setNormal(jsonNormal2);
		vo2.setItemStyle(itemStyle2);

		Integer[][] point2 = { { 10, 1 } };
		vo2.setData(point2);

		MarkLine markLine2 = new MarkLine();
		markLine2.setAnimation(false);

		LineStyle lineStyle2 = new LineStyle();
		Normal normal2 = new Normal();
		normal2.setType("solid");
		lineStyle2.setNormal(normal2);
		markLine2.setLineStyle(lineStyle2);

		List<JSONArray> data2 = new ArrayList<JSONArray>();
		JSONArray array2 = new JSONArray();
		JSONObject startPoint2 = new JSONObject();
		startPoint2.put("coord", new Integer[] { 0, 1 });
		startPoint2.put("symbol", "none");

		JSONObject endPoint2 = new JSONObject();
		endPoint2.put("coord", new Integer[] { 10, 1 });
		endPoint2.put("symbol", "none");

		array2.add(startPoint2);
		array2.add(endPoint2);
		data2.add(array2);
		markLine2.setData(data2);
		vo2.setMarkLine(markLine2);

		JSONArray series = new JSONArray();
		series.add(vo);
		series.add(vo2);
		System.out.println(series);
	}
}
