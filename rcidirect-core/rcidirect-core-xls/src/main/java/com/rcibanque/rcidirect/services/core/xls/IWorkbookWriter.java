package com.rcibanque.rcidirect.services.core.xls;

import java.io.IOException;

import com.rcibanque.rcidirect.services.core.xls.impl.Header;

public interface IWorkbookWriter {

	ISheetWriter createSheet(String pSheetName, Header[] pHeader);

	byte[] getContent() throws IOException;

}
