package com.example.communities_post_website.repository;

import com.example.communities_post_website.model.Post;
import com.example.communities_post_website.model.User;
import com.example.communities_post_website.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    //Finding the user by Post,
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
