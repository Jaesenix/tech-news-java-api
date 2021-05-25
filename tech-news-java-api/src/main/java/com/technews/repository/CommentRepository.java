package main.java.com.technews.repository;

import main.java.com.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends  JpaRepository<Comment, Integer> {

    List<Comment> findAllCommentsByPostId(int postId);
}
