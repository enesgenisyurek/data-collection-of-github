package com.githubIssueTrack.githubIssueTrack.githubIssue;

import com.githubIssueTrack.githubIssueTrack.githubMilestone.GithubMilestone;
import com.githubIssueTrack.githubIssueTrack.githubComment.GithubComment;
import com.githubIssueTrack.githubIssueTrack.githubLabel.GithubLabel;
import com.githubIssueTrack.githubIssueTrack.githubPullRequest.GithubPullRequest;
import com.githubIssueTrack.githubIssueTrack.githubUser.GithubUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class GithubIssue {



    private Long id;
    private Long number;
    private String url;
    private String repository_url;
    private String html_url;
    private String node_id;
    private String title;
    private String body;
    private String state;
    private Date created_at;
    private Date updated_at;
    private Date closed_at;

    private GithubUser user;

    private GithubUser assignee;
    private List<GithubUser> assignees;

    private Long comments;
    private String comments_url;
    private List<GithubComment> commentList;

    private List<GithubLabel> labels;
    private GithubMilestone milestone;
    private GithubPullRequest pull_request;

}
