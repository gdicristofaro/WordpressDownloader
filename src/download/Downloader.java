package download;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Downloader {
	public static void p(String s) {
		System.out.println(s);
	}
	
	public static void main(String[] args) {	
		if (args.length != 2) {
			p("There needs to be two args: [wordpress xml file] [download folder location]");
			return;
		}
		
		File xml = new File(args[0]);
		String basepath = args[1];
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList nl = doc.getElementsByTagName("wp:attachment_url");
			p("nodes length is " + nl.getLength());

			int counter = 0;
			for (int i=0; i < nl.getLength(); i++) {

				Node an = nl.item(i);
				if(an.getNodeType()==Node.ELEMENT_NODE) {
					NodeList nl2 = an.getChildNodes();

					for(int i2=0; i2<nl2.getLength(); i2++) {
						Node an2 = nl2.item(i2);

						String address = an2.getTextContent();
						File toDownload = new File(address);
						File output = new File(basepath + File.separator + toDownload.getName());
						if (!output.exists()) {
							p((counter++) + " " + toDownload.getName());
						}
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
