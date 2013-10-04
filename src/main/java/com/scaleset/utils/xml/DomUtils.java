package com.scaleset.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DomUtils {

    private final static ThreadLocal<Exception> lastException = new ThreadLocal<Exception>();

    public final static DocumentBuilder DocumentBuilder = documentBuilder();

    public static DocumentBuilder documentBuilder() {
        DocumentBuilder result = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute("http://apache.org/xml/features/dom/defer-node-expansion", Boolean.FALSE);
        dbf.setNamespaceAware(true);
        try {
            result = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
        }
        return result;
    }

    public static Exception lastException() {
        return lastException.get();
    }

    public static Document document(File src) {
        Document result = null;
        InputStream in = null;
        try {
            in = new FileInputStream(src);
            result = document(in);
        } catch (Exception e) {
            lastException.set(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    public static Document document(InputStream in) {
        Document result = null;
        try {
            result = DocumentBuilder.parse(in);
        } catch (Exception e) {
            lastException.set(e);
        }
        return result;
    }

    public static Document document(InputSource in) {
        Document result = null;
        try {
            result = DocumentBuilder.parse(in);
        } catch (Exception e) {
            lastException.set(e);
        }
        return result;
    }

    public static String childText(Node parent, String name, String fallback) {
        Element child = child(parent, name);
        return text(child, fallback);
    }

    public static String text(Element element, String fallback) {
        return (element == null) ? fallback : element.getTextContent();
    }

    public static String text(Element element) {
        return text(element, null);
    }

    public static List<Element> children(Node parent, String name) {
        List<Element> results = new ArrayList<Element>();

        if (parent == null || name == null) {
            return results;
        }
        Node first = parent.getFirstChild();
        if (first == null) {
            return results;
        }
        for (Node node = first; node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String childName = name(node);
                if (name.equals(childName)) {
                    results.add((Element) node);
                }
            }
        }
        return results;
    }

    public static Element child(Node parent, String name) {
        if (parent == null || name == null) {
            return null;
        }
        Node first = parent.getFirstChild();
        if (first == null) {
            return null;
        }
        for (Node node = first; node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String childName = name(node);
                if (name.equals(childName)) {
                    return (Element) node;
                }
            }
        }
        return null;
    }

    public static Element elementByTagName(Node parent, String name) {
        Element result = null;
        if (parent instanceof Element) {
            NodeList nodes = ((Element) parent).getElementsByTagName(name);
            if (nodes.getLength() > 0) {
                result = (Element) nodes.item(0);
            }
        } else if (parent instanceof Document) {
            NodeList nodes = ((Document) parent).getElementsByTagName(name);
            if (nodes.getLength() > 0) {
                result = (Element) nodes.item(0);
            }
        }
        return result;
    }

    /**
     * Return the name for the given node.
     * 
     * @param node
     *            The input node
     * @return The name of the node or null if the node doesn't have name.
     */
    public static String name(Node node) {
        String name = node.getLocalName();
        if (name == null) {
            name = node.getNodeName();
        }
        return name;
    }

    /**
     * Return the text content of the named attribute with the element.
     * 
     * @param element
     *            The input element
     * @param name
     *            The name of the attribute whose text value is being returned.
     * @param fallback
     *            The value to return if there is no text content for the
     *            attribute
     * @return The attribute text or the fallback value if element is null or no
     *         attribute is present.
     */
    public static String attrText(Element element, String name, String fallback) {
        return (element != null && element.hasAttribute(name)) ? element.getAttribute(name) : fallback;
    }
}
