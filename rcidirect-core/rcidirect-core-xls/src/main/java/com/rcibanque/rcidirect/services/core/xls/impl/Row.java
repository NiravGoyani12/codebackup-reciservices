package com.rcibanque.rcidirect.services.core.xls.impl;

import java.util.ArrayList;
import java.util.List;

import com.rcibanque.rcidirect.services.core.xls.ICellReader;
import com.rcibanque.rcidirect.services.core.xls.ICellWriter;
import com.rcibanque.rcidirect.services.core.xls.IRowReader;
import com.rcibanque.rcidirect.services.core.xls.IRowWriter;

final class Row implements IRowWriter, IRowReader {


	private final Sheet _sheet;

	private final org.apache.poi.ss.usermodel.Row _rowImpl;

	private int _columnNb = 0;


	Row(Sheet pSheet, int pRowNb) {
		this(pSheet, pSheet.getSheetImpl().createRow(pRowNb));
	}

	Row(Sheet pSheet, org.apache.poi.ss.usermodel.Row pRowImpl) {
		super();
		_sheet = pSheet;
		_rowImpl = pRowImpl;
	}


	Sheet getSheet() {
		return _sheet;
	}

	org.apache.poi.ss.usermodel.Row getRowImpl() {
		return _rowImpl;
	}


	@Override
	public List<ICellReader> getCells() {
		List<ICellReader> res = new ArrayList<>();

		for(int i=0; i<_rowImpl.getLastCellNum(); i++) {

			res.add(new Cell(this, _rowImpl.getCell(i), getSheet().getColumnCellStyle(i)));
		}

		return res;
	}


	@Override
	public ICellWriter createCell() {

		return createCell(getSheet().getColumnCellStyle(_columnNb));
	}

	Cell createCell(CellStyle pCellStyle) {

		return new Cell(this, _columnNb++, pCellStyle);
	}


}
