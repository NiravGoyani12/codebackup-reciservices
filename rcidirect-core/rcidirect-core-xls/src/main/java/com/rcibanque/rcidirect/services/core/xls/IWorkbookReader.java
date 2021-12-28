package com.rcibanque.rcidirect.services.core.xls;

import com.rcibanque.rcidirect.services.core.xls.impl.Header;

public interface IWorkbookReader {

	ISheetReader getSheet(String pSheetName, Header[] pHeader);

}
