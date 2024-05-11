package com.guide.common.tool.jenkins;

import lombok.Data;

@Data
public class MavenProject
{

    private String groupId;
    private String artifactId;
    private String version;
    private String svnPath;

    public MavenProject(String groupId, String artifactId, String version)
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }
}
