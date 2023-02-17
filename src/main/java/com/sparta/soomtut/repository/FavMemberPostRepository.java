package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.FavMemberPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface FavMemberPostRepository extends JpaRepository<FavMemberPost, Long> {
    @Query(value = "select f from fav_member_post where f.post.id=:postid and f.member.id=:memberid", nativeQuery = true)
    Optional<FavMemberPost> findByPostIdAndMemberId(@Param("postid")Long postId, @Param("memberid")Long memberId);
    boolean existsByPostIdAndMemberId(Long id, Long memberId);

    List<FavMemberPost> findAllByMemberId(Long memberId);

    Optional<FavMemberPost> findByPostId(Long postId);

    List<FavMemberPost> findAllFavMembers(Pageable pageable);

}
