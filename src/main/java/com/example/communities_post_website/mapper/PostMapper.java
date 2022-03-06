package com.example.communities_post_website.mapper;

import com.example.communities_post_website.dto.PostRequest;
import com.example.communities_post_website.dto.PostResponse;
import com.example.communities_post_website.model.Post;
import com.example.communities_post_website.model.Subreddit;
import com.example.communities_post_website.model.User;
import com.example.communities_post_website.repository.CommentRepository;
import com.example.communities_post_website.repository.VoteRepository;
import com.example.communities_post_website.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import static com.example.springredditclone.model.VoteType.DOWNVOTE;
import static com.example.springredditclone.model.VoteType.UPVOTE;

/*interface -> abstract because we need information to map the new field.
voteCount, commentCount and duration
- As I said before, instead of updating PostService.java class
with this logic, we can just change
the interface to abstract class and inject the needed
dependencies into our class to fill the new field’s information.
https://programmingtechie.com/2020/02/24/build-a-full-stack-reddit-clone-with-spring-boot-and-angular-part-7/#What_are_we_building
* */

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    //If the target and the source have the same name,
    //We dont have to declare it in here.
    //For example.
    //@Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")//creat a new Post object
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                    authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
