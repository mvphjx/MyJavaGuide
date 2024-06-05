package com.guide.common.tool.jenkins;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VersionChangeUtil
{
    public static void updateVersion(String path, String version)
    {
        Document document = XmlUtil.readXML(path);
        Element rootElement = XmlUtil.getRootElement(document);
        Element parentElement = XmlUtil.getElement(rootElement, "parent");
        Element versionElement = null;
        if (parentElement != null)
        {
            versionElement = XmlUtil.getElement(parentElement, "version");
        }
        else
        {
            versionElement = XmlUtil.getElement(rootElement, "version");
        }
        versionElement.setTextContent(version);
        String content = XmlUtil.toStr(document);
        FileUtil.writeString(content, path, "UTF-8");
    }

    public static void main(String[] args)
    {
        VersionChangeUtil.updateVersion(
                "C:\\mic\\source\\platform\\java\\trunk\\common-j\\copyright-info-hisign\\pom.xml", "2.0.0-SNAPSHOT");
    }
}
