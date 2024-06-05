package com.guide.common.tool.jenkins;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class ProjectUtil
{
    public static List<MavenProject> getAll(String path) throws Exception
    {
        List<MavenProject> result = new ArrayList<>();
        //查找path目录下  所有pom.xml文件
        List<File> pomFiles = FileUtil.loopFiles(path, new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                if (file.isDirectory())
                {
                    return false;
                }
                if ("pom.xml".equals(file.getName()))
                {
                    return true;
                }
                return false;
            }
        });
        for (File pomFile : pomFiles)
        {
            result.add(parsePom(pomFile));
        }
        return result;
    }

    public static MavenProject parsePom(File pomFile) throws Exception
    {
        // 解析pom.xml文件以获取Maven项目信息
        // 你可以使用Hutool的XML工具类来解析XML文件
        Document document = XmlUtil.readXML(pomFile);
        Element rootElement = XmlUtil.getRootElement(document);
        Element artifactIdElement = XmlUtil.getElement(rootElement, "artifactId");
        Element parentElement = XmlUtil.getElement(rootElement, "parent");
        Element groupIdElement = null;
        Element versionElement = null;
        if (parentElement != null)
        {
            groupIdElement = XmlUtil.getElement(parentElement, "groupId");
            versionElement = XmlUtil.getElement(parentElement, "version");
        }
        else
        {
            groupIdElement = XmlUtil.getElement(rootElement, "groupId");
            versionElement = XmlUtil.getElement(rootElement, "version");
        }
        String artifactId = artifactIdElement.getTextContent();
        String groupId = groupIdElement.getTextContent();
        String version = versionElement.getTextContent();
        MavenProject mavenProject = new MavenProject(groupId, artifactId, version);
        //C:\mic\source\platform\java\trunk\habis-web\habis_web_ares
        String path = pomFile.getPath();
        path = path.replace("\\", "/");
        //https://192.168.128.210/svn/mic/source/platform/java/trunk/habis-web/habis_web_ares/
        String svnPath = path.replace("C:/", "https://192.168.128.210/svn/");
        svnPath = svnPath.replace("/pom.xml", "");
        mavenProject.setSvnPath(svnPath);
        mavenProject.setFilePath(pomFile.getPath());
        mavenProject.setJobName("platform-" + mavenProject.getArtifactId());
        return mavenProject;
    }
}
