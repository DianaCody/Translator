package org.samsu.youdaofanyi.document;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.eclipse.core.resources.IFile;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class DocumentHelper {

	public static Document getDocument(InputStream inputStream) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(inputStream);
			return document;
		} catch (JDOMException e) {

		} catch (IOException e) {

		}
		return null;
	}

	public static Element getRootElement(InputStream inputStream) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(inputStream);
			return document.getRootElement();
		} catch (JDOMException e) {

		} catch (IOException e) {

		}
		return null;
	}

	public static Element getRootElement(Reader reader) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(reader);
		return document.getRootElement();
	}

	public static boolean getAttributeBooleanValue(Element element, String attribute) {
		String value = element.getAttributeValue(attribute);
		return Boolean.valueOf(value);
	}

	public static void write(Document document, OutputStream outputStream) throws Exception {
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("    ");
		XMLOutputter outputter = new XMLOutputter(format);
		outputter.output(document, outputStream);
	}

	public static void write(Document document, Writer writer) throws Exception {
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("    ");
		XMLOutputter outputter = new XMLOutputter(format);
		outputter.output(document, writer);
	}

	public static void saveDocToFile(Document doc, IFile file) {
		OutputStream os = null;
		try {
			File renewFile = file.getLocation().toFile();
			if (!renewFile.exists()) {
				renewFile.createNewFile();
			}
			if (!renewFile.canWrite()) {
				renewFile.setWritable(true);
			}
			os = new BufferedOutputStream(new FileOutputStream(renewFile));
			DocumentHelper.write(doc, os);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Element getRootElement(File file) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(file);
			return document.getRootElement();
		} catch (JDOMException e) {

		} catch (IOException e) {

		}
		return null;
	}
}
