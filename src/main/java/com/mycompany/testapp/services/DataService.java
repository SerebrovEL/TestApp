package com.mycompany.testapp.services;

import com.mycompany.testapp.model.Mkb10;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class DataService {

	private final Logger log = LoggerFactory.getLogger(DataService.class); // 1. Объявляем переменную логгера

	public List<Mkb10> getData() {
		List<Mkb10> data = new ArrayList<>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(getFileFromResource("mkb10.xml"));
			document.getDocumentElement().normalize();
			NodeList nList = document.getElementsByTagName("entry");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					data.add(getMkb10((Element) nNode));
				}
			}
		} catch (IOException | URISyntaxException | ParserConfigurationException | SAXException ex) {
			log.error("Error: " + ex.getLocalizedMessage());
		}
		return data;
	}

	private Mkb10 getMkb10(Element eElement) {
		return new Mkb10(
			eElement.getElementsByTagName("ID").item(0) != null
			? eElement.getElementsByTagName("ID").item(0).getTextContent() : "",
			eElement.getElementsByTagName("REC_CODE").item(0) != null
			? eElement.getElementsByTagName("REC_CODE").item(0).getTextContent() : "",
			eElement.getElementsByTagName("MKB_CODE").item(0) != null
			? eElement.getElementsByTagName("MKB_CODE").item(0).getTextContent() : "",
			eElement.getElementsByTagName("MKB_NAME").item(0) != null
			? eElement.getElementsByTagName("MKB_NAME").item(0).getTextContent() : "",
			eElement.getElementsByTagName("ID_PARENT").item(0) != null
			? eElement.getElementsByTagName("ID_PARENT").item(0).getTextContent() : "",
			eElement.getElementsByTagName("ACTUAL").item(0) != null
			? eElement.getElementsByTagName("ACTUAL").item(0).getTextContent() : ""
		);
	}

	private File getFileFromResource(String fileName) throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return new File(resource.toURI());
		}
	}

}
