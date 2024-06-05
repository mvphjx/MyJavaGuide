package com.guide.common.tool.jenkins;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JenkinsMavenJobCreator
{
    /**
     * 1. 遍历所有pom.xml
     * 2. 创建job
     * 3. 添加到view
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        List<MavenProject> projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk");
        projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk\\common-j");
        JenkinsUtil jenkinsUtil = new JenkinsUtil("http://192.168.129.168:18080/", "mic", "micadmin");
        for (MavenProject project : projects)
        {
            if (jenkinsUtil.getJob(project) == null)
            {
                jenkinsUtil.createJob(project);
                jenkinsUtil.append("abis", project);
            }
        }
    }

}
