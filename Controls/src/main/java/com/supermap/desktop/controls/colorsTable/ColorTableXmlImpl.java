package com.supermap.desktop.controls.colorsTable;

import com.supermap.data.ColorDictionary;
import com.supermap.desktop.Application;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;

/**
 * Created by lixiaoyao on 2017/3/8.
 */
public class ColorTableXmlImpl implements IColorTableXml {

	private Document document;
	private final String rootName = "ColorTable";
	private final String headerName = "Header";
	private final String itemsName = "Items";
	private final String itemName = "Item";
	private final String itemCount = "ItemCount";
	private final String colorName = "color";
	private final String valueName = "value";
	private final String typeName = "SCTU";

	public ColorTableXmlImpl() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			Application.getActiveApplication().getOutput().output(e);
		}
	}

	//  Create XML file based on file name and color table
	@Override
	public boolean createXml(String fileName, ColorDictionary colorDictionary) {
		boolean createXMLResult = true;
		Color[] colors = colorDictionary.getColors();
		double[] keys = colorDictionary.getKeys();
		Element root = this.document.createElement(rootName);
		root.setAttribute("version", "1.0");
		Element header = this.document.createElement(headerName);
		Element type = this.document.createElement("Type");
		type.appendChild(this.document.createTextNode(typeName));
		Element ItemCount = this.document.createElement(itemCount);
		ItemCount.appendChild(this.document.createTextNode(String.valueOf(keys.length)));
		header.appendChild(type);
		header.appendChild(ItemCount);

		Element items = this.document.createElement(itemsName);
		for (int i = 0; i < keys.length; i++) {
			Element item = this.document.createElement(itemName);
			item.setAttribute(colorName, aRGBtoHexValue(colors[i].getAlpha()) + aRGBtoHexValue(colors[i].getRed()) + aRGBtoHexValue(colors[i].getGreen()) + aRGBtoHexValue(colors[i].getBlue()));
			item.setAttribute(valueName, String.valueOf(keys[i]));
			items.appendChild(item);
		}
		root.appendChild(header);
		root.appendChild(items);
		this.document.appendChild(root);
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			pw.close();
		} catch (TransformerConfigurationException e) {
			createXMLResult = false;
		} catch (IllegalArgumentException e) {
			createXMLResult = false;
		} catch (FileNotFoundException e) {
			createXMLResult = false;
		} catch (TransformerException e) {
			createXMLResult = false;
		}
		return createXMLResult;
	}

	//  Create XML file based on file name and color table,by SAX
	public boolean createXMLBySax(String fileName, ColorDictionary colorDictionary) {
		boolean createXMLResult = true;
		Color[] colors = colorDictionary.getColors();
		double[] keys = colorDictionary.getKeys();
		try {
			// 创建工厂
			SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler handler = factory.newTransformerHandler();
			Transformer info = handler.getTransformer();
			// 是否自动添加额外的空白
			info.setOutputProperty(OutputKeys.INDENT, "yes");
			// 设置字符编码
			info.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			info.setOutputProperty(OutputKeys.VERSION, "1.0");
			info.setOutputProperty(OutputKeys.STANDALONE, "false");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			// 保存创建的xml
			StreamResult result = new StreamResult(pw);
			handler.setResult(result);
			// 开始xml
			handler.startDocument();
			AttributesImpl impl = new AttributesImpl();
			impl.clear();//清空属性
			impl.addAttribute("", "", "version", "", "1.0");
			handler.startElement("", "", rootName, impl);
			impl.clear();
			handler.startElement("", "", headerName, impl);
			impl.clear();
			handler.startElement("", "", "Type", impl);
			handler.characters(typeName.toCharArray(), 0, typeName.length());
			handler.endElement("", "", "Type");
			impl.clear();
			handler.startElement("", "", itemCount, impl);
			String keysLength = String.valueOf(keys.length);
			handler.characters(keysLength.toCharArray(), 0, keysLength.length());
			handler.endElement("", "", itemCount);
			handler.endElement("", "", headerName);
			impl.clear();
			handler.startElement("", "", itemsName, impl);
			for (int i = 0; i < keys.length; i++) {
				impl.clear();
				impl.addAttribute("", "", valueName, "", String.valueOf(keys[i]));
				impl.addAttribute("", "", colorName, "", aRGBtoHexValue(colors[i].getAlpha()) + aRGBtoHexValue(colors[i].getRed()) + aRGBtoHexValue(colors[i].getGreen()) + aRGBtoHexValue(colors[i].getBlue()));
				handler.startElement("", "", itemName, impl);
				handler.endElement("", "", itemName);
			}
			handler.endElement("", "", itemsName);
			handler.endElement("", "", rootName);
			handler.endDocument();
			pw.close();
		} catch (Exception e) {
			createXMLResult = false;
		}
		return createXMLResult;
	}

	//   Parse the color table file according to the file name
	//    warning  parameter filename  meaning file-path
	@Override
	public ColorDictionary parserXml(String fileName) {
		ColorDictionary colorDictionary = new ColorDictionary();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(fileName);
			Document document = db.parse(file);

			Element root = document.getDocumentElement();// get root Node
			NodeList childNodes = root.getChildNodes();
			NodeList items = null;
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node.getNodeName().equals(itemsName)) {
					items = node.getChildNodes();
					break;
				}
			}
			colorDictionary.clear();
			for (int j = 0; j < items.getLength(); j++) {
				Node node = items.item(j);
				if (node.getNodeName().equals(itemName)) {
					colorDictionary.setColor(Double.valueOf(((Element) node).getAttribute(valueName)), fromStrToARGB(((Element) node).getAttribute(colorName)));
				}
			}

		} catch (FileNotFoundException e) {
			return null;
		} catch (ParserConfigurationException e) {
			return null;
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return colorDictionary;
	}

	//  Converts the imported string color to ARGB
	private Color fromStrToARGB(String str) {
		String str1 = str.substring(0, 2);
		String str2 = str.substring(2, 4);
		String str3 = str.substring(4, 6);
		String str4 = str.substring(6, 8);
		int alpha = Integer.parseInt(str1, 16);
		int red = Integer.parseInt(str2, 16);
		int green = Integer.parseInt(str3, 16);
		int blue = Integer.parseInt(str4, 16);
		Color color = new Color(red, green, blue, alpha);
		return color;
	}

	//  Convert ARGB to 16 string
	private String aRGBtoHexValue(int number) {
		String result=Integer.toHexString(number & 0xff);
		while (result.length()<2){
			result="0"+result;
		}
		return result.toUpperCase();
	}
}
