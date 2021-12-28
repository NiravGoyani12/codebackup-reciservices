package com.rcibanque.rcidirect.services.core.xls.impl;

public final class Header {


	String _name;

	int _width;

	CellStyle _cellStyle;

	String[] _values;


	public Header(String pName, int pWidth, CellStyle pCellStyle) {
		super();
		_name = pName;
		_width = pWidth;
		_cellStyle = pCellStyle;
	}

	public Header(String pName, int pWidth, CellStyle pCellStyle, String[] pValues) {
		this(pName, pWidth, pCellStyle);
		_values = pValues;
	}

}