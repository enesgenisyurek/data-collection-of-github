package com.githubIssueTrack.githubIssueTrack.githubPullRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GithubPullRequest {

    private String url;
    private String html_url;
    private String diff_url;
    private String patch_url;

}
