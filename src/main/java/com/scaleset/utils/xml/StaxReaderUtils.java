/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.scaleset.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.namespace.QName;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public final class StaxReaderUtils {

    private static final BlockingQueue<XMLInputFactory> NS_AWARE_INPUT_FACTORY_POOL;

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
        NS_AWARE_INPUT_FACTORY_POOL = new LinkedBlockingQueue<XMLInputFactory>(i);
    }

    private StaxReaderUtils() {
    }

    /**
     * Return a cached, namespace-aware, factory.
     * 
     * @return
     */
    private static XMLInputFactory getXMLInputFactory() {
        XMLInputFactory f = NS_AWARE_INPUT_FACTORY_POOL.poll();
        if (f == null) {
            f = XMLInputFactory.newInstance();
            f.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
        }
        return f;
    }

    private static void returnXMLInputFactory(XMLInputFactory factory) {
        NS_AWARE_INPUT_FACTORY_POOL.offer(factory);
    }

    /**
     * Return a new factory so that the caller can set sticky parameters.
     * 
     * @param nsAware
     * @return
     */
    public static XMLInputFactory createXMLInputFactory(boolean nsAware) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, nsAware);
        return factory;
    }

    public static XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) {
        XMLInputFactory factory = getXMLInputFactory();
        try {
            return factory.createFilteredReader(reader, filter);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamReader", e);
        } finally {
            returnXMLInputFactory(factory);
        }
    }

    public static void nextEvent(XMLStreamReader dr) {
        try {
            dr.next();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static boolean toNextText(EnhancedXMLStreamReader reader) {
        if (reader.getEventType() == XMLStreamReader.CHARACTERS) {
            return true;
        }

        try {
            int depth = reader.getDepth();
            int event = reader.getEventType();
            while (reader.getDepth() >= depth && reader.hasNext()) {
                if (event == XMLStreamReader.CHARACTERS && reader.getDepth() == depth + 1) {
                    return true;
                }
                event = reader.next();
            }
            return false;
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static boolean toNextTag(XMLStreamReader reader) {
        try {
            // advance to first tag.
            int x = reader.getEventType();
            while (x != XMLStreamReader.START_ELEMENT && x != XMLStreamReader.END_ELEMENT && reader.hasNext()) {
                x = reader.next();
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
        return true;
    }

    public static boolean toNextTag(EnhancedXMLStreamReader reader, QName endTag) {
        try {
            int depth = reader.getDepth();
            int event = reader.getEventType();
            while (reader.getDepth() >= depth && reader.hasNext()) {
                if (event == XMLStreamReader.START_ELEMENT && reader.getName().equals(endTag)
                        && reader.getDepth() == depth + 1) {
                    return true;
                }
                event = reader.next();
            }
            return false;
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    /**
     * Returns true if currently at the start of an element, otherwise move
     * forwards to the next element start and return true, otherwise false is
     * returned if the end of the stream is reached.
     */
    public static boolean skipToStartOfElement(XMLStreamReader in) throws XMLStreamException {
        for (int code = in.getEventType(); code != XMLStreamReader.END_DOCUMENT; code = in.next()) {
            if (code == XMLStreamReader.START_ELEMENT) {
                return true;
            }
        }
        return false;
    }

    public static boolean toNextElement(EnhancedXMLStreamReader dr) {
        if (dr.getEventType() == XMLStreamReader.START_ELEMENT) {
            return true;
        }
        if (dr.getEventType() == XMLStreamReader.END_ELEMENT) {
            return false;
        }
        try {
            int depth = dr.getDepth();

            for (int event = dr.getEventType(); dr.getDepth() >= depth && dr.hasNext(); event = dr.next()) {
                if (event == XMLStreamReader.START_ELEMENT && dr.getDepth() == depth + 1) {
                    return true;
                } else if (event == XMLStreamReader.END_ELEMENT) {
                    depth--;
                }
            }

            return false;
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static boolean skipToStartOfElement(EnhancedXMLStreamReader in) throws XMLStreamException {
        for (int code = in.getEventType(); code != XMLStreamReader.END_DOCUMENT; code = in.next()) {
            if (code == XMLStreamReader.START_ELEMENT) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param in
     * @param encoding
     * @param ctx
     * @return
     */
    public static XMLStreamReader createXMLStreamReader(InputStream in, String encoding) {
        if (encoding == null) {
            encoding = "UTF-8";
        }

        XMLInputFactory factory = getXMLInputFactory();
        try {
            return factory.createXMLStreamReader(in, encoding);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        } finally {
            returnXMLInputFactory(factory);
        }
    }

    /**
     * @param in
     * @return
     */
    public static XMLStreamReader createXMLStreamReader(InputStream in) {
        XMLInputFactory factory = getXMLInputFactory();
        try {
            return factory.createXMLStreamReader(in);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        } finally {
            returnXMLInputFactory(factory);
        }
    }

    public static XMLStreamReader createXMLStreamReader(String systemId, InputStream in) {
        XMLInputFactory factory = getXMLInputFactory();
        try {
            return factory.createXMLStreamReader(systemId, in);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        } finally {
            returnXMLInputFactory(factory);
        }
    }

    /**
     * @param reader
     * @return
     */
    public static XMLStreamReader createXMLStreamReader(Reader reader) {
        XMLInputFactory factory = getXMLInputFactory();
        try {
            return factory.createXMLStreamReader(reader);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        } finally {
            returnXMLInputFactory(factory);
        }
    }

    /**
     * @param reader
     * @return
     * @throws FileNotFoundException
     */
    public static XMLStreamReader createXMLStreamReader(File file) throws FileNotFoundException {
        return createXMLStreamReader(new FileInputStream(file));
    }

    /**
     * Reads a QName from the element text. Reader must be positioned at the
     * start tag.
     */
    public static QName qname(XMLStreamReader reader) throws XMLStreamException {
        String value = reader.getElementText();
        if (value == null) {
            return null;
        }

        int index = value.indexOf(":");

        if (index == -1) {
            return new QName(value);
        }

        String prefix = value.substring(0, index);
        String localName = value.substring(index + 1);
        String ns = reader.getNamespaceURI(prefix);

        if ((!(prefix == null || "".equals(prefix)) && ns == null) || localName == null) {
            throw new RuntimeException("Invalid QName in mapping: " + value);
        }

        if (ns == null) {
            return new QName(localName);
        }

        return new QName(ns, localName, prefix);
    }

}
