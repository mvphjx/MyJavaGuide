package com.guide.common.jenkins;

import com.guide.common.tool.jenkins.JenkinsUtil;
import com.guide.common.tool.jenkins.MavenProject;
import com.guide.common.tool.jenkins.ProjectUtil;
import com.guide.common.tool.jenkins.VersionChangeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JenkinsTest
{
    /**
     * 修改所有pom文件版本
     *
     * @throws Exception
     */
    @Test
    public void changeAllVersion() throws Exception
    {
        List<MavenProject> all = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk");
        for (MavenProject mavenProject : all)
        {
            VersionChangeUtil.updateVersion(mavenProject.getFilePath(), "2.0.0-SNAPSHOT");
        }
    }

    @Test
    public void creatAllJob() throws Exception
    {

        List<MavenProject> projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk");
        //projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk\\common-j");
        //projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk\\abis-common-j\\abisbase");
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

    @Test
    public void deleteAllJob() throws Exception
    {
        List<MavenProject> projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk");
        //projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk\\common-j");
        JenkinsUtil jenkinsUtil = new JenkinsUtil("http://192.168.129.168:18080/", "mic", "micadmin");
        for (MavenProject project : projects)
        {
            jenkinsUtil.deleteJob(project);
        }
    }

    @Test
    public void findDiffJob() throws Exception
    {
        List<MavenProject> diffProjects = new ArrayList<>();
        List<MavenProject> projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk");
        //projects = ProjectUtil.getAll("C:\\mic\\source\\platform\\java\\trunk\\common-j");
        JenkinsUtil jenkinsUtil = new JenkinsUtil("http://192.168.129.168:18080/", "mic", "micadmin");
        for (MavenProject project : projects)
        {
            if (jenkinsUtil.getJob(project) != null)
            {
                // 查找 MavenProject的groupId、artifactId相同的数据,add到diffProjects
                for (MavenProject diffProject : projects)
                {
                    if (project.getGroupId().equals(diffProject.getGroupId()) && project.getArtifactId()
                            .equals(diffProject.getArtifactId()))
                    {
                        diffProjects.add(diffProject);
                    }
                }
            }
        }
        System.out.println(diffProjects.size() + "/" + projects.size());
        for (MavenProject project : diffProjects)
        {
            System.out.println(project.getGroupId() + ":" + project.getArtifactId());
        }

    }
}
