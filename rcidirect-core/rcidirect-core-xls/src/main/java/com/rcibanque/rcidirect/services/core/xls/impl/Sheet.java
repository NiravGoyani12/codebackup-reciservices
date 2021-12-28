package com.rcibanque.rcidirect.services.core.xls.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.rcibanque.rcidirect.services.core.xls.IRowReader;
import com.rcibanque.rcidirect.services.core.xls.ISheetReader;
import com.rcibanque.rcidirect.services.core.xls.ISheetWriter;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;

final class Sheet implements ISheetWriter, ISheetReader {


	private final Workbook _workbook;

	private final int _sheetIndex;

	private final org.apache.poi.ss.usermodel.Sheet _sheetImpl;

	private final Header[] _header;

	private int _rowNb = 0;


	Sheet(Workbook pWorkbook, int pSheetIndex, String pSheetName, Header[] pHeader) {
		this(pWorkbook, pSheetIndex, pWorkbook.getWorkbookImpl().createSheet(pSheetName), pHeader);
		createHeaderRow();
	}

	Sheet(Workbook pWorkbook, int pSheetIndex, org.apache.poi.ss.usermodel.Sheet pSheetImpl, Header[] pHeader) {
		super();
		_workbook = pWorkbook;
		_sheetIndex = pSheetIndex;
		_sheetImpl = pSheetImpl;
		_header = pHeader;
	}


	Workbook getWorkbook() {
		return _workbook;
	}

	int getSheetIndex() {
		return _sheetIndex;
	}

	org.apache.poi.ss.usermodel.Sheet getSheetImpl() {
		return _sheetImpl;
	}


	@Override
	public List<IRowReader> getRows() {
		List<IRowReader> res = new ArrayList<>();

		for(int i=0 + (_header != null ? 1 : 0); i<_sheetImpl.getPhysicalNumberOfRows(); i++) {

			res.add(new Row(this, _sheetImpl.getRow(i)));
		}

		return res;
	}

	@Override
	public Row createRow() {

		return new Row(this, _rowNb++);
	}


	void createHeaderRow() {

		if(_header != null) {

			Row headerRow = createRow();

			_sheetImpl.createFreezePane(0, 1);

			for (int i=0; i<_header.length; i++) {
				Header header = _header[i];

				// Label
				headerRow.createCell(CellStyle.HEADER).setValue(header._name);

				// Width
				_sheetImpl.setColumnWidth(i, header._width * 256);

				// Validation
				if(header._values != null) {

					createCellValidation(i, header._values);
				}
			}
		}
	}


	private void createCellValidation(int pColumnNb, String[] pValues) {

		Sheet validationDataSheet = _workbook.getValidationDataSheet();

		// ----------------------------
		// Validation data content
		// ----------------------------

		// Value lines
		int startRowNb = -1;
		int endRowNb = -1;
		for(String value : IteratorUtils.nullableIterable(pValues)) {
			Row row = validationDataSheet.createRow();

			int rowNb = row.getRowImpl().getRowNum() + 1;
			if(startRowNb == -1) {
				startRowNb = rowNb;
			}
			endRowNb = rowNb;

			row.createCell().setValue(value);
		}

		// Empty line
		validationDataSheet.createRow();

		// ----------------------------
		// Validation data configuration
		// ----------------------------
		Name namedCell = _workbook.getWorkbookImpl().createName();
		namedCell.setNameName("VD_" + pColumnNb);
		namedCell.setRefersToFormula(validationDataSheet.getSheetImpl().getSheetName() + "!$A$" + startRowNb + ":$A$" + endRowNb);

		CellRangeAddressList addressList = new CellRangeAddressList(1, Short.MAX_VALUE, pColumnNb, pColumnNb);
		DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(namedCell.getNameName());
		HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);

		_sheetImpl.addValidationData(dataValidation);
	}


	CellStyle getColumnCellStyle(int pColumnNb) {

		return _header != null && _header.length > pColumnNb ? _header[pColumnNb]._cellStyle : null;
	}

}
