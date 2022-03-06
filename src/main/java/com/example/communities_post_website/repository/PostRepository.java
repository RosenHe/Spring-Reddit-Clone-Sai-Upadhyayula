package com.example.communities_post_website.repository;

import com.example.communities_post_website.model.Post;
import com.example.communities_post_website.model.Subreddit;
import com.example.communities_post_website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
