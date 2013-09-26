package com.scaleset.utils.xml;

import java.io.OutputStream;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

public class StaxWriterUtils {

    private static final BlockingQueue<XMLOutputFactory> OUTPUT_FACTORY_POOL;

    static {
        int i = 20;

        try {
            String s = System.getProperty("org.apache.cxf.staxutils.pool-size", "-1");
            i = Integer.parseInt(s);
        } catch (Throwable t) {
            // ignore
            i = 20;
        }
        if (i <= 0) {
            i = 20;
        }
        OUTPUT_FACTORY_POOL = new LinkedBlockingQueue<XMLOutputFactory>(i);
    }

    private static XMLOutputFactory getXMLOutputFactory() {
        XMLOutputFactory f = OUTPUT_FACTORY_POOL.poll();
        if (f == null) {
            f = XMLOutputFactory.newInstance();
        }
        return f;
    }

    private static void returnXMLOutputFactory(XMLOutputFactory factory) {
        OUTPUT_FACTORY_POOL.offer(factory);
    }

    public static XMLStreamWriter createXMLStreamWriter(OutputStream out) {
        return createXMLStreamWriter(out, null);
    }

    public static XMLStreamWriter createXMLStreamWriter(Writer out) {
        XMLOutputFactory factory = getXMLOutputFactory();
        try {
            return factory.createXMLStreamWriter(out);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamWriter", e);
        } finally {
            returnXMLOutputFactory(factory);
        }
    }

    public static XMLStreamWriter createXMLStreamWriter(OutputStream out, String encoding) {
        if (encoding == null) {
            encoding = "UTF-8";
        }
        XMLOutputFactory factory = getXMLOutputFactory();
        try {
            return factory.createXMLStreamWriter(out, encoding);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamWriter", e);
        } finally {
            returnXMLOutputFactory(factory);
        }
    }

    public static XMLStreamWriter createXMLStreamWriter(Result r) {
        XMLOutputFactory factory = getXMLOutputFactory();
        try {
            return factory.createXMLStreamWriter(r);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamWriter", e);
        } finally {
            returnXMLOutputFactory(factory);
        }
    }

    public static void textElement(XMLStreamWriter writer, String localName, String text, boolean writeEmpty)
            throws XMLStreamException {
        if (!((text == null) || "".equals(text)) || writeEmpty) {
            writer.writeStartElement(localName);
            if (text != null) {
                writer.writeCharacters(text);
            }
            writer.writeEndElement();
        }
    }

    public static void startElement(XMLStreamWriter writer, String prefix, String name, String namespace)
            throws XMLStreamException {
        if (prefix == null) {
            prefix = "";
        }

        if (namespace != null && namespace.length() > 0) {
            writer.writeStartElement(prefix, name, namespace);
            if (prefix.length() > 0) {
                writer.writeNamespace(prefix, namespace);
                writer.setPrefix(prefix, namespace);
            } else {
                writer.writeDefaultNamespace(namespace);
                writer.setDefaultNamespace(namespace);
            }
        } else {
            writer.writeStartElement(name);
            writer.writeDefaultNamespace("");
            writer.setDefaultNamespace("");
        }
    }

}
