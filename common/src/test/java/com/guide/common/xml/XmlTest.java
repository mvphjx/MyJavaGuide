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

    @Test
    public void getVersion()
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n"
                + "    <modelVersion>4.0.0</modelVersion>\n" + "    <parent>\n"
                + "        <groupId>com.hisign.pu.abis</groupId>\n" + "        <artifactId>web_parent</artifactId>\n"
                + "        <version>1.0.7-SNAPSHOT</version>\n" + "    </parent>\n" + "\t<packaging>jar</packaging>\n"
                + "    <artifactId>habis_web_ares</artifactId>\n" + "    <dependencies>\n" + "        <dependency>\n"
                + "            <groupId>com.hisign.pu.abis</groupId>\n"
                + "            <artifactId>habis_web_lp</artifactId>\n"
                + "            <version>${hsabis.version}</version>\n" + "        </dependency>\n"
                + "        <dependency>\n" + "            <groupId>com.hisign.pu.abis</groupId>\n"
                + "            <artifactId>habis_web_tp</artifactId>\n"
                + "            <version>${hsabis.version}</version>\n" + "        </dependency>\n"
                + "        <dependency>\n" + "            <groupId>com.hisign.pu.abis</groupId>\n"
                + "            <artifactId>ares_app_lib</artifactId>\n"
                + "            <version>${hsabis.version}</version>\n" + "        </dependency>\n"
                + "        <dependency>\n" + "            <groupId>com.hisign.pu.abis</groupId>\n"
                + "            <artifactId>ares_protobuf_lib</artifactId>\n"
                + "            <version>${hsabis.version}</version>\n" + "        </dependency>\n"
                + "        <dependency>\n" + "            <groupId>com.hisign.pu.abis</groupId>\n"
                + "            <artifactId>abisfpt</artifactId>\n"
                + "            <version>${hsabis.version}</version>\n" + "            <scope>compile</scope>\n"
                + "        </dependency>\n" + "    </dependencies>\n" + "\n" + "</project>";
        Document doc = XmlUtil.parseXml(xml);
        Object modelVersion = XmlUtil.getByXPath("/project/modelVersion", doc, XPathConstants.STRING);
        //TODO
        System.out.println(modelVersion);
    }

    @Test
    public void JenkinsXml()
    {
        String xml = "<?xml version='1.1' encoding='UTF-8'?>\n" + "<maven2-moduleset plugin=\"maven-plugin@3.23\">\n"
                + "  <actions/>\n" + "  <description></description>\n"
                + "  <keepDependencies>false</keepDependencies>\n" + "  <properties/>\n"
                + "  <scm class=\"hudson.scm.SubversionSCM\" plugin=\"subversion@2.17.3\">\n" + "    <locations>\n"
                + "      <hudson.scm.SubversionSCM_-ModuleLocation>\n"
                + "        <remote>https://192.168.128.210/svn/mic/source/platform/java/trunk/common-j/parent</remote>\n"
                + "        <credentialsId>a66b0987-b42a-4fc7-abb3-0d5d14e952f9</credentialsId>\n"
                + "        <local>.</local>\n" + "        <depthOption>infinity</depthOption>\n"
                + "        <ignoreExternalsOption>true</ignoreExternalsOption>\n"
                + "        <cancelProcessOnExternalsFail>true</cancelProcessOnExternalsFail>\n"
                + "      </hudson.scm.SubversionSCM_-ModuleLocation>\n" + "    </locations>\n"
                + "    <excludedRegions></excludedRegions>\n" + "    <includedRegions></includedRegions>\n"
                + "    <excludedUsers></excludedUsers>\n" + "    <excludedRevprop></excludedRevprop>\n"
                + "    <excludedCommitMessages></excludedCommitMessages>\n"
                + "    <workspaceUpdater class=\"hudson.scm.subversion.UpdateUpdater\"/>\n"
                + "    <ignoreDirPropChanges>false</ignoreDirPropChanges>\n"
                + "    <filterChangelog>false</filterChangelog>\n" + "    <quietOperation>true</quietOperation>\n"
                + "  </scm>\n" + "  <canRoam>true</canRoam>\n" + "  <disabled>false</disabled>\n"
                + "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n"
                + "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" + "  <triggers>\n"
                + "    <hudson.triggers.SCMTrigger>\n" + "      <spec>H/5 * * * *</spec>\n"
                + "      <ignorePostCommitHooks>false</ignorePostCommitHooks>\n" + "    </hudson.triggers.SCMTrigger>\n"
                + "  </triggers>\n" + "  <concurrentBuild>false</concurrentBuild>\n" + "  <rootModule>\n"
                + "    <groupId>com.hisign.pu.abis</groupId>\n" + "    <artifactId>parent</artifactId>\n"
                + "  </rootModule>\n" + "  <aggregatorStyleBuild>true</aggregatorStyleBuild>\n"
                + "  <incrementalBuild>false</incrementalBuild>\n"
                + "  <ignoreUpstremChanges>false</ignoreUpstremChanges>\n"
                + "  <ignoreUnsuccessfulUpstreams>false</ignoreUnsuccessfulUpstreams>\n"
                + "  <archivingDisabled>false</archivingDisabled>\n"
                + "  <siteArchivingDisabled>false</siteArchivingDisabled>\n"
                + "  <fingerprintingDisabled>false</fingerprintingDisabled>\n"
                + "  <resolveDependencies>false</resolveDependencies>\n" + "  <processPlugins>false</processPlugins>\n"
                + "  <mavenValidationLevel>-1</mavenValidationLevel>\n" + "  <runHeadless>false</runHeadless>\n"
                + "  <disableTriggerDownstreamProjects>false</disableTriggerDownstreamProjects>\n"
                + "  <blockTriggerWhenBuilding>true</blockTriggerWhenBuilding>\n"
                + "  <settings class=\"jenkins.mvn.FilePathSettingsProvider\">\n" + "    <path></path>\n"
                + "  </settings>\n" + "  <globalSettings class=\"jenkins.mvn.DefaultGlobalSettingsProvider\"/>\n"
                + "  <reporters/>\n" + "  <publishers/>\n" + "  <buildWrappers/>\n" + "  <prebuilders/>\n"
                + "  <postbuilders/>\n" + "  <runPostStepsIfResult>\n" + "    <name>FAILURE</name>\n"
                + "    <ordinal>2</ordinal>\n" + "    <color>RED</color>\n"
                + "    <completeBuild>true</completeBuild>\n" + "  </runPostStepsIfResult>\n" + "</maven2-moduleset>";
        Document doc = XmlUtil.parseXml(xml);
        // 修改 maven2-moduleset.rootModule.groupId
        Element rootElement = XmlUtil.getRootElement(doc);
        Element groupId = XmlUtil.getElementByXPath("//rootModule/groupId", doc);
        groupId.setTextContent("com.hisign.pu.abis.test");
        // 修改 maven2-moduleset.rootModule.artifactId
        Element artifactId = XmlUtil.getElementByXPath("//rootModule/artifactId", doc);
        artifactId.setTextContent("test");
        //修改 maven2-moduleset.scm.hudson.scm.SubversionSCM_-ModuleLocation.remote
        Element remote = XmlUtil.getElementByXPath("//scm//remote", doc);
        remote.setTextContent("svn.....");
        //输出修改后的xml
        System.out.println(XmlUtil.toStr(doc));

    }
}
