package com.rcibanque.rcidirect.services.core.xls.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;

import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.utils.MathUtils;
import com.rcibanque.rcidirect.services.core.xls.ICellReader;
import com.rcibanque.rcidirect.services.core.xls.ICellWriter;
import com.rcibanque.rcidirect.services.core.xls.exception.InvalidCellContentException;

final class Cell implements ICellWriter, ICellReader {


	private final Row _row;

	private final CellStyle _cellStyle;

	private final org.apache.poi.ss.usermodel.Cell _cellImpl;


	Cell(Row pRow, int pColumnNb, CellStyle pCellStyle) {
		this(pRow, pRow.getRowImpl().createCell(pColumnNb), pCellStyle);
		_cellImpl.setCellStyle(_row.getSheet().getWorkbook().getCellStyle(_cellStyle));
	}

	Cell(Row pRow, org.apache.poi.ss.usermodel.Cell pCell, CellStyle pCellStyle) {
		super();
		_row = pRow;
		_cellImpl = pCell;
		_cellStyle = pCellStyle != null ? pCellStyle : CellStyle.STRING;
	}


	Row getRow() {
		return _row;
	}

	org.apache.poi.ss.usermodel.Cell getCellImpl() {
		return _cellImpl;
	}


	@Override
	public Object getValue() throws InvalidCellContentException {
		Object value = null;

		try {

			switch(_cellStyle) {
			case HEADER:
			case STRING:
				if(_cellImpl != null && _cellImpl.getCellType().equals(CellType.NUMERIC)) {
					// Assuming that Excel has converted a number such as '5' into a numeric value, even if on a string styled cell
					value = ConvertUtils.toString((int) _cellImpl.getNumericCellValue());
				}
				else if(_cellImpl != null) {
					value = StringUtils.trimToNull(_cellImpl.getStringCellValue());
				}
				break;
			case INTEGER:
				value = isEmpty() ? null : Integer.valueOf((int) _cellImpl.getNumericCellValue());
				break;
			case LONG:
				value = isEmpty() ? null : Long.valueOf((long) _cellImpl.getNumericCellValue());
				break;
			case DOUBLE:
				value = isEmpty() ? null : Double.valueOf(_cellImpl.getNumericCellValue());
				break;
			case AMOUNT:
				value = isEmpty() ? null : MathUtils.valueOfAmount(_cellImpl.getNumericCellValue());
				break;
			case RATE:
			case PERCENTAGE:
				value = isEmpty() ? null : Double.valueOf(_cellImpl.getNumericCellValue());
				break;
			case DATE:
				value = isEmpty() ? null : _cellImpl.getDateCellValue();
				break;
			default:
				throw new IllegalArgumentException("Unsupported cell style");
			}
		}
		catch(IllegalStateException | NumberFormatException e) {
			// Invalid numeric cell content
			throw new InvalidCellContentException(e, _cellImpl);
		}

		return value;
	}

	private boolean isEmpty() {

		return _cellImpl == null || _cellImpl.getCellType().equals(CellType.BLANK);
	}


	@Override
	public void setValue(Object pValue) {

		if(pValue != null) {

			switch(_cellStyle) {
			case HEADER:
			case STRING:
				_cellImpl.setCellValue(pValue.toString());
				break;
			case INTEGER:
				_cellImpl.setCellValue((Integer) pValue);
				break;
			case LONG:
				_cellImpl.setCellValue((Long) pValue);
				break;
			case DOUBLE:
				_cellImpl.setCellValue((Double) pValue);
				break;
			case AMOUNT:
				_cellImpl.setCellValue(((BigDecimal) pValue).doubleValue());
				break;
			case RATE:
			case PERCENTAGE:
				_cellImpl.setCellValue((Double) pValue);
				break;
			case DATE:
				_cellImpl.setCellValue((Date) pValue);
				break;
			default:
				throw new IllegalArgumentException("Unsupported cell style");
			}
		}
	}

}
