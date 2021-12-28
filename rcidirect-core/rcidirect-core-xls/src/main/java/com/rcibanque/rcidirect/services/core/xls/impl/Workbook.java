package com.rcibanque.rcidirect.services.core.xls.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.rcibanque.rcidirect.services.core.xls.ISheetReader;
import com.rcibanque.rcidirect.services.core.xls.ISheetWriter;
import com.rcibanque.rcidirect.services.core.xls.IWorkbookReader;
import com.rcibanque.rcidirect.services.core.xls.IWorkbookWriter;

final class Workbook implements IWorkbookWriter, IWorkbookReader {

	private static final String VALIDATION_DATA_SHEET_NAME = "VALIDATION_DATA";


	private final HSSFWorkbook _workbookImpl;

	private int _sheetNb = 0;

	private final Map<CellStyle, org.apache.poi.ss.usermodel.CellStyle> _cellStyles = new HashMap<>();

	private Sheet _validationDataSheet;


	Workbook(HSSFWorkbook pWorkbookImpl) {
		super();
		_workbookImpl = pWorkbookImpl;
		createStyles();
	}

	HSSFWorkbook getWorkbookImpl() {
		return _workbookImpl;
	}


	@Override
	public ISheetReader getSheet(String pSheetName, Header[] pHeader) {

		return new Sheet(this, -1, _workbookImpl.getSheet(pSheetName), pHeader);
	}

	@Override
	public ISheetWriter createSheet(String pSheetName, Header[] pHeader) {

		return new Sheet(this, _sheetNb++, pSheetName, pHeader);
	}

	Sheet getValidationDataSheet() {

		// Inline validation data is limited to 255 characters
		// To avoid this limitation, place validation data in a separate sheet and create named range

		if(_validationDataSheet == null) {
			_validationDataSheet = (Sheet) createSheet(VALIDATION_DATA_SHEET_NAME, null);
			_workbookImpl.setSheetHidden(_validationDataSheet.getSheetIndex(), true);
		}

		return _validationDataSheet;
	}


	private void createStyles() {

		for(CellStyle cellStyle : CellStyle.values()) {
			switch(cellStyle) {
			case HEADER:
				_cellStyles.put(cellStyle, createCellStyleHeader());
				break;
			case STRING:
				_cellStyles.put(cellStyle, createCellStyleString());
				break;
			case INTEGER:
				_cellStyles.put(cellStyle, createCellStyleInteger());
				break;
			case LONG:
				_cellStyles.put(cellStyle, createCellStyleLong());
				break;
			case DOUBLE:
				_cellStyles.put(cellStyle, createCellStyleDouble());
				break;
			case AMOUNT:
				_cellStyles.put(cellStyle, createCellStyleAmount());
				break;
			case RATE:
				_cellStyles.put(cellStyle, createCellStyleRate());
				break;
			case PERCENTAGE:
				_cellStyles.put(cellStyle, createCellStylePercentage());
				break;
			case DATE:
				_cellStyles.put(cellStyle, createCellStyleDate());
				break;
			default:
				throw new IllegalStateException("Unsupported cell style");
			}
		}
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleHeader() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		BorderStyle thin = BorderStyle.THIN;
		short black = IndexedColors.BLACK.getIndex();

		style.setBorderRight(thin);
		style.setRightBorderColor(black);
		style.setBorderBottom(thin);
		style.setBottomBorderColor(black);
		style.setBorderLeft(thin);
		style.setLeftBorderColor(black);
		style.setBorderTop(thin);
		style.setTopBorderColor(black);

		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

		Font headerFont = _workbookImpl.createFont();
		headerFont.setBold(true);
		style.setFont(headerFont);

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleString() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		style.setWrapText(true);

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleInteger() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		// Noting special

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleLong() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		// Noting special

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleAmount() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		style.setDataFormat(_workbookImpl.createDataFormat().getFormat("#,##0.00"));

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleDouble() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		style.setDataFormat(_workbookImpl.createDataFormat().getFormat("#,##0.0000"));

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleRate() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		style.setDataFormat(_workbookImpl.createDataFormat().getFormat("0.0000%"));

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStylePercentage() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		style.setDataFormat(_workbookImpl.createDataFormat().getFormat("0.00%"));

		return style;
	}

	private org.apache.poi.ss.usermodel.CellStyle createCellStyleDate() {

		org.apache.poi.ss.usermodel.CellStyle style = _workbookImpl.createCellStyle();

		style.setAlignment(HorizontalAlignment.CENTER);
		style.setDataFormat(_workbookImpl.createDataFormat().getFormat("dd/MM/yyyy"));

		return style;
	}


	org.apache.poi.ss.usermodel.CellStyle getCellStyle(CellStyle pCellStyle) {

		return _cellStyles.get(pCellStyle);
	}


	@Override
	public byte[] getContent() throws IOException {

		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			_workbookImpl.write(baos);
			return baos.toByteArray();
		}
	}


}
