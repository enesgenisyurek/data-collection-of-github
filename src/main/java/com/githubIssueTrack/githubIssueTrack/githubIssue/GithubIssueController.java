package com.githubIssueTrack.githubIssueTrack.githubIssue;

import com.githubIssueTrack.githubIssueTrack.GithubAssignee.GithubAssignee;
import com.githubIssueTrack.githubIssueTrack.githubComment.GithubComment;
import com.githubIssueTrack.githubIssueTrack.githubUser.GithubUser;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/github")
public class GithubIssueController {

    @Autowired
    private GithubIssueService githubIssueService;

    @GetMapping("/repos/{owner}/{repo}/issues")
    public List<GithubIssue> getAllRepoIssues(@PathVariable String owner, @PathVariable String repo) {
        return githubIssueService.getAllRepoIssues(owner,repo);
    }

}

