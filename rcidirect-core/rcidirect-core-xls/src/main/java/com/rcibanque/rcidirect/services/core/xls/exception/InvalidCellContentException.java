package com.rcibanque.rcidirect.services.core.xls.exception;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;

public final class InvalidCellContentException extends Exception {

	private static final long serialVersionUID = 1L;


	private final String _cellNumber;


	public InvalidCellContentException(Exception e, Cell pCell) {
		super(e.getMessage(), e);
		_cellNumber = pCell == null ? "?" : CellReference.convertNumToColString(pCell.getColumnIndex()) + (pCell.getRowIndex() + 1);
	}


	public String getCellNumber() {
		return _cellNumber;
	}

}
