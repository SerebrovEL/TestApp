package com.mycompany.testapp.services;

import com.mycompany.testapp.model.Mkb10;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataService {

	private static final Collection<Mkb10> data = new ArrayList<>();

	public DataService() {
		try {
			readFromExcel("mkb10.xlsx");
		} catch (IOException | URISyntaxException ex) {
			Logger.getLogger(DataService.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static Collection<Mkb10> getData() {
		return data;
	}

	/*
        The resource URL is not working in the JAR
        If we try to access a file that is inside a JAR,
        It throws NoSuchFileException (linux), InvalidPathException (Windows)

        Resource URL Sample: file:java-io.jar!/json/file1.json
	 */
	private File getFileFromResource(String fileName) throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			// failed if files have whitespaces or special characters
			//return new File(resource.getFile());
			return new File(resource.toURI());
		}
	}

	private void readFromExcel(String file) throws IOException, URISyntaxException {

		try (XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(getFileFromResource(file)))) {
			XSSFSheet myExcelSheet = myExcelBook.getSheet("МКБ-10");
			for (int i = 4; i <= myExcelSheet.getLastRowNum(); i++) {
				XSSFRow row = myExcelSheet.getRow(i);
				data.add(
					new Mkb10(row.getCell(0).getStringCellValue(),
						row.getCell(1).getStringCellValue(),
						row.getCell(2).getStringCellValue())
				);
			}
		}
	}

}
