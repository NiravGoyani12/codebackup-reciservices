package com.rcibanque.rcidirect.services.core.xls;

import com.rcibanque.rcidirect.services.core.xls.exception.InvalidCellContentException;

public interface ICellReader {

	Object getValue() throws InvalidCellContentException;

}
