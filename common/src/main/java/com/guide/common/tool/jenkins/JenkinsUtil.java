package com.guide.common.tool.jenkins;

import cn.hutool.core.util.XmlUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpConnection;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Data
public class JenkinsUtil
{
    private static JenkinsServer jenkins;
    private String url;
    private String password;
    private String username;

    public JenkinsUtil(String url, String username, String password) throws URISyntaxException
    {
        jenkins = new JenkinsServer(new URI(url), username, password);
    }

    public Job getJob(MavenProject mavenProject) throws IOException
    {
        return jenkins.getJob(mavenProject.getJobName());
    }

    public void createJob(MavenProject mavenProject) throws IOException
    {
        String demoJobName = "parent";
        String xml = jenkins.getJobXml(demoJobName);
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
        String jobName = mavenProject.getArtifactId();
        if (!jobName.startsWith("platform-"))
        {
            jobName = "platform-" + jobName;
        }
        System.out.println("do create:" + mavenProject.getJobName());
        jenkins.createJob(jobName, XmlUtil.toStr(doc), true);
    }

    public void append(String viewName, MavenProject mavenProject) throws IOException
    {
        JobWithDetails job = jenkins.getJob("parent");
        JenkinsHttpConnection client = job.getClient();
        //访问  http://192.168.129.168:18080/view/abis/config.xml
        String xml = client.get("view/" + viewName + "/config.xml");
        Document doc = XmlUtil.parseXml(xml);
        Element jobNames = XmlUtil.getElementByXPath("//jobNames", doc);
        Element newElement = XmlUtil.appendChild(jobNames, "string");
        //新增job
        newElement.setTextContent(mavenProject.getJobName());
        jenkins.updateView(viewName, XmlUtil.toStr(doc));
    }

    public void deleteJob(MavenProject mavenProject) throws IOException
    {
        if ("parent".equalsIgnoreCase(mavenProject.getJobName()))
        {
            return;
        }
        if ("platform-parent".equalsIgnoreCase(mavenProject.getJobName()))
        {
            return;
        }
        if (this.getJob(mavenProject) != null)
        {
            System.out.println("do delete:" + mavenProject.getJobName());
        }
        else
        {
            System.out.println("not find:" + mavenProject.getJobName());
            return;
        }
        jenkins.deleteJob(mavenProject.getJobName(), true);
    }

}
