package com.sz.jhl.util;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.List;

public class XmlUtil {

    public static String getHeadNode(String respMsg, String headNode) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));

        Node packageHead = doc.selectSingleNode("/TX");
        Node respCodeNode = packageHead.selectSingleNode(headNode);

        return respCodeNode.getText();
    }

    public static String getTxtInfo(String respMsg, String headNode) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
        Node packageHead = doc.selectSingleNode("/TX/TX_INFO");
        Node respCodeNode = packageHead.selectSingleNode(headNode);

        return respCodeNode.getText();
    }

    public static List<Element> getDetailListInfo(String respMsg, String headNode) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
        Element element = doc.getRootElement();
        List<Element> elements = element.element("TX_INFO").element("DETAILLIST").elements();
        return elements;
    }

}
