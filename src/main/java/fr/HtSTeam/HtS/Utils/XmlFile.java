package fr.HtSTeam.HtS.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.HtSTeam.HtS.Main;

public class XmlFile {

	private File file;
	private Document doc;

	public XmlFile(String parent, String fileName) {
		file = new File(Main.plugin.getDataFolder() + "/" + parent + "/", fileName + ".xml");
		if (file.exists()) {
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		if (!file.exists())
			file.getParentFile().mkdirs();
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty(OutputKeys.METHOD, "xml");
			tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			
			tf.transform(source, result);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	public HashMap<String, ArrayList<String>> getOptions() {
		HashMap<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();
		NodeList node_list = getNodeList("option");
		for (int i = 0; i < node_list.getLength(); i++)
			if (!node_list.item(i).hasChildNodes()) {
				final int f = i;
				options.put(getAttributeValue(node_list.item(i), "name"), new ArrayList<String>() {{ add(getValue(node_list.item(f))); }});
			} else if (node_list.item(i).hasChildNodes()) {
				ArrayList<String> option_elements = new ArrayList<String>();
				NodeList node_child = node_list.item(i).getChildNodes();
				for (int y = 0; y < node_child.getLength(); y++)
					if (node_child.item(y).getNodeName().equals("element"))
						option_elements.add(getValue(node_child.item(y)));
				options.put(getAttributeValue(node_list.item(i), "name"), option_elements);
			}
		return options;
	}
	
	public void root(final String node_name, final Map<String, String> attributes, final String node_value) {
		if(doc.getFirstChild() != null)
			return;
		Element root = doc.createElement(node_name);
		doc.appendChild(root);
		if (node_value != null)
			root.appendChild(doc.createTextNode(node_value));
		if (attributes != null)	
			for (String atrr_name : attributes.keySet()) {
				Attr attr = doc.createAttribute(atrr_name);
				attr.setValue(attributes.get(atrr_name));
				root.setAttributeNode(attr);
			}
	}
	
	public void sub(final String node_name, final Map<String, String> attributes, final String node_value) {
		Node parent = doc.getFirstChild();
		while (parent.getLastChild() != null)
			parent = parent.getLastChild();
		Element child = doc.createElement(node_name);
		if (node_value != null)
			child.appendChild(doc.createTextNode(node_value));
		if (attributes != null)	
			for (String atrr_name : attributes.keySet()) {
				Attr attr = doc.createAttribute(atrr_name);
				attr.setValue(attributes.get(atrr_name));
				child.setAttributeNode(attr);
			}
		parent.appendChild(child);
	}
	
	public void set(final String node_name, final Map<String, String> attributes, final String node_value,
			final String parent_name, final String parent_attr, final String parent_attrvalue) {
		if (doc.getDocumentElement().getNodeName() == null || parent_name == null)
			return;

		Element node = doc.createElement(node_name);
		if (node_value != null)
			node.appendChild(doc.createTextNode(node_value));
		if(attributes != null)
			for (String atrr_name : attributes.keySet()) {
				Attr attr = doc.createAttribute(atrr_name);
				attr.setValue(attributes.get(atrr_name));
				node.setAttributeNode(attr);
			}
		NodeList parents = doc.getElementsByTagName(parent_name);
		for (int i = 0; i < parents.getLength(); i++)
			if (parents.item(i).getNodeType() == Node.ELEMENT_NODE)
				if (((Element) parents.item(i)).getAttribute(parent_attr) == parent_attrvalue || (parent_attr == null && parent_attrvalue == null))
					((Element) parents.item(i)).appendChild(node);
	}
	
	private NodeList getNodeList(final String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	private String getAttributeValue(final Node node, final String attribute) {
		return ((Element) node).getAttribute(attribute);
	}
	
	private String getValue(final Node node) {
		return ((Element) node).getTextContent();
	}
}
