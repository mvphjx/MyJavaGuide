package com.guide.common.xml;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;

@Slf4j
public class XmlTest
{
    @Test
    public void xml()
    {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<DATAS>\n" + "    <TYPE>1</TYPE>\n"
                + "    <DESCRIPTION>2</DESCRIPTION>\n" + "    <RETURNMSG>\n" + "        <COUNT>300</COUNT>\n"
                + "        <DATA>\n" + "            <BEGINID>1</BEGINID>\n" + "            <CZ>操作</CZ>\n"
                + "            <JGBH>机构编号</JGBH>\n" + "            <SJBH>上级编号</SJBH>\n"
                + "            <MC>机构名称</MC>\n" + "            <JC>机构简称</JC>\n" + "            <DM>机构代码</DM>\n"
                + "            <SJDM>上级机构代码</SJDM>\n" + "            <XH>序</XH>\n" + "            <LB>类别</LB>\n"
                + "            <CJSJ>创建时间</CJSJ>\n" + "            <CJRY>创建人员</CJRY>\n"
                + "            <XGSJ>修改时间</XGSJ>\n" + "            <XGRY>修改人员</XGRY>\n"
                + "            <DH>机构电话</DH>\n" + "            <DZ>机构地址</DZ>\n"
                + "            <SSJZ>所属警种</SSJZ>\n" + "        </DATA>\n" + "        <DATA>\n"
                + "            <BEGINID>2</BEGINID>\n" + "            <CZ>操作</CZ>\n"
                + "            <JGBH>机构编号</JGBH>\n" + "            <SJBH>上级编号</SJBH>\n"
                + "            <MC>机构名称</MC>\n" + "            <JC>机构简称</JC>\n" + "            <DM>机构代码</DM>\n"
                + "            <SJDM>上级机构代码</SJDM>\n" + "            <XH>序</XH>\n" + "            <LB>类别</LB>\n"
                + "            <CJSJ>创建时间</CJSJ>\n" + "            <CJRY>创建人员</CJRY>\n"
                + "            <XGSJ>修改时间</XGSJ>\n" + "            <XGRY>修改人员</XGRY>\n"
                + "            <DH>机构电话</DH>\n" + "            <DZ>机构地址</DZ>\n"
                + "            <SSJZ>所属警种</SSJZ>\n" + "        </DATA>\n" + "    </RETURNMSG>\n" + "</DATAS>\n";
        Document doc = XmlUtil.parseXml(xml);
        Element element = XmlUtil.getElementByXPath("//DATAS", doc);
        NodeList data = element.getElementsByTagName("DATA");
        for (int i = 0; i < data.getLength(); i++)
        {
            Node item = data.item(i);
            Object beginId = XmlUtil.getByXPath("BEGINID", item, XPathConstants.STRING);
            System.out.println(XmlUtil.toStr(item));
            System.out.println(beginId);
        }

    }

    @Test
    public void getBeginId()
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA>\n" + "            <BEGINID>1</BEGINID>\n"
                + "            <CZ>操作</CZ>\n" + "            <JGBH>机构编号</JGBH>\n"
                + "            <SJBH>上级编号</SJBH>\n" + "            <MC>机构名称</MC>\n"
                + "            <JC>机构简称</JC>\n" + "            <DM>机构代码</DM>\n"
                + "            <SJDM>上级机构代码</SJDM>\n" + "            <XH>序</XH>\n" + "            <LB>类别</LB>\n"
                + "            <CJSJ>创建时间</CJSJ>\n" + "            <CJRY>创建人员</CJRY>\n"
                + "            <XGSJ>修改时间</XGSJ>\n" + "            <XGRY>修改人员</XGRY>\n"
                + "            <DH>机构电话</DH>\n" + "            <DZ>机构地址</DZ>\n"
                + "            <SSJZ>所属警种</SSJZ>\n" + "        </DATA>";
        Document doc = XmlUtil.parseXml(xml);
        Object beginId = XmlUtil.getByXPath("//BEGINID", doc, XPathConstants.STRING);
        System.out.println(beginId);
    }
}
