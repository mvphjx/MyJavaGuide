package com.guide.common.jenkins;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Optional;
import com.guide.common.tool.jenkins.JenkinsMavenJobCreator;
import com.guide.common.tool.jenkins.MavenProject;
import com.guide.common.tool.jenkins.ProjectUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpConnection;
import com.offbytwo.jenkins.helper.JenkinsVersion;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.MavenJobWithDetails;
import com.offbytwo.jenkins.model.View;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Jenkins 常用 REST API介绍(Java 客户端)
 * https://www.jianshu.com/p/ae7e003dfb2c/
 */
public class JenkinsUtilTest
{
    private static final String JENKINS_URL = "http://192.168.129.168:18080";

    @Test
    public void getAll() throws Exception
    {
        List<MavenProject> all = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk");
        System.out.println(all.size());
    }

    @Test
    public void parsePom() throws Exception
    {
        String pomPath = "C:\\mic\\source\\platform\\java\\trunk\\abis-common-j\\abisbase\\pom.xml";
        pomPath = "C:\\mic\\source\\platform\\java\\trunk\\common-j\\baselib\\pom.xml";
        pomPath = "C:\\mic\\source\\platform\\java\\trunk\\common-j\\copyright-info-hisign\\pom.xml";
        MavenProject mavenProject = ProjectUtil.parsePom(new File(pomPath));
        System.out.println(JSONUtil.toJsonStr(mavenProject));
    }

    @Test
    public void getConfig() throws Exception
    {
        JenkinsServer jenkins = new JenkinsServer(new URI("http://192.168.129.168:18080/"), "mic", "micadmin");
        String xml = jenkins.getJobXml("parent");
        System.out.println(xml);
    }

    @Test
    public void createJob() throws Exception
    {
        String json = "{\"groupId\":\"com.hisign.pu.abis\",\"svnPath\":\"https://192.168.128.210/svn/mic/source/platform/java/trunk/common-j/copyright-info-hisign\",\"version\":\"1.0.7-SNAPSHOT\",\"artifactId\":\"copyright-info-hisign\"}";
        String demoJobName = "parent";
        MavenProject mavenProject = JSONUtil.toBean(json, MavenProject.class);
        JenkinsServer jenkins = new JenkinsServer(new URI("http://192.168.129.168:18080/"), "mic", "micadmin");
        String xml = jenkins.getJobXml(demoJobName);
        JobWithDetails job = jenkins.getJob(demoJobName);
        Document doc = XmlUtil.parseXml(xml);
        // 修改 maven2-moduleset.rootModule.groupId
        Element groupId = XmlUtil.getElementByXPath("//rootModule/groupId", doc);
        groupId.setTextContent(mavenProject.getGroupId());
        // 修改 maven2-moduleset.rootModule.artifactId
        Element artifactId = XmlUtil.getElementByXPath("//rootModule/artifactId", doc);
        artifactId.setTextContent(mavenProject.getArtifactId());
        //修改 maven2-moduleset.scm.hudson.scm.SubversionSCM_-ModuleLocation.remote
        Element remote = XmlUtil.getElementByXPath("//scm//remote", doc);
        remote.setTextContent(mavenProject.getSvnPath());
        jenkins.createJob(mavenProject.getArtifactId(), XmlUtil.toStr(doc), true);
        View abis = jenkins.getView("abis");
        FolderJob folderJob = new FolderJob();

    }

    @Test
    public void getView() throws Exception
    {
        JenkinsServer jenkins = new JenkinsServer(new URI("http://192.168.129.168:18080/"), "mic", "micadmin");
        JobWithDetails job = jenkins.getJob("parent");
        JenkinsHttpConnection client = job.getClient();
        //访问  http://192.168.129.168:18080/view/abis/config.xml
        String xml = client.get("view/abis/config.xml");
        System.out.println(xml);
        Document doc = XmlUtil.parseXml(xml);
        Element jobNames = XmlUtil.getElementByXPath("//jobNames", doc);
        Element newElement = XmlUtil.appendChild(jobNames, "string");
        //新增job
        newElement.setTextContent("test");
        System.out.println(XmlUtil.toStr(doc));
    }
}
