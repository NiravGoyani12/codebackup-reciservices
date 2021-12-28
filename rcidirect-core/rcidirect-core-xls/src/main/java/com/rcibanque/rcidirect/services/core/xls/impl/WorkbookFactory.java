package com.rcibanque.rcidirect.services.core.xls.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rcibanque.rcidirect.services.core.xls.IWorkbookReader;
import com.rcibanque.rcidirect.services.core.xls.IWorkbookWriter;

public final class WorkbookFactory {

	private WorkbookFactory() {}


	/**
	 * @return {@link IWorkbookWriter Workbook writer}
	 */
	public static IWorkbookWriter newWorkbookWriter() {

		return new Workbook(new HSSFWorkbook());
	}

	/**
	 * @param pContent The workbook file content
	 * @return {@link IWorkbookWriter Workbook reader}
	 * @throws IOException If content is invalid
	 */
	public static IWorkbookReader newWorkbookReader(byte[] pContent) throws IOException {

		return new Workbook(new HSSFWorkbook(new ByteArrayInputStream(pContent)));
	}

}
