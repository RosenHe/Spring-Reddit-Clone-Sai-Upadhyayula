package com.example.communities_post_website.repository;

import com.example.communities_post_website.model.Comment;
import com.example.communities_post_website.model.Post;
import com.example.communities_post_website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
