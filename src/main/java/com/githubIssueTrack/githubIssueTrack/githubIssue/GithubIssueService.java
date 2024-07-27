
package com.githubIssueTrack.githubIssueTrack.githubIssue;


import com.githubIssueTrack.githubIssueTrack.githubComment.GithubComment;
import com.githubIssueTrack.githubIssueTrack.githubComment.GithubCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GithubIssueService {

    @Value("${github.api.base-url}")
    private String githubApiBaseUrl;

    @Value("${github.auth.token}")
    private String authToken;

    @Value("${spring.codec.max-in-memory-size}")
    private String maxMemorySize;

    @Autowired
    private GithubCommentService githubCommentService;



    public List<GithubIssue> getAllRepoIssues(String owner, String repo) {
        WebClient webClient = WebClient.builder()
                .baseUrl(githubApiBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 500))
                .build();

        List<GithubIssue> allIssues = new ArrayList<>();
        int page = 1;
        boolean hasNextPage = true;

        while (page<3) {
            try {
                List<GithubIssue> issues = webClient.get()
                        .uri("/repos/{owner}/{repo}/issues?page={page}&per_page=100&state=closed", owner, repo, page)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                                Mono.error(new RuntimeException("Client error: " + clientResponse.statusCode())))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                                Mono.error(new RuntimeException("Server error: " + clientResponse.statusCode())))
                        .bodyToFlux(GithubIssue.class)
                        .collectList()
                        .block();

                    for (GithubIssue issue : issues) {
                        List<GithubComment> comments = githubCommentService.getIssueComments(owner, repo, issue.getNumber());
                        issue.setCommentList(comments);

                    }
                    allIssues.addAll(issues);
                    page++;

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        return allIssues;
    }

}
