package com.technews.repository;

import com.technews.model.Vote;

public interface VoteRepository extends JpaRepository<Vote , Integer> {

    @Query ("SELECT count(*) FROM Vote v where v.postId = :id")
    int countVotesByPostId(@Param("id") Integer id);
}
