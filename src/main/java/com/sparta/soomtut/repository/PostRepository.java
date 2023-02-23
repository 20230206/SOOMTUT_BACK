package com.sparta.soomtut.repository;

import com.sparta.soomtut.dto.response.PostResponseDto;
import com.sparta.soomtut.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository <Post, Long> {
    Optional<Post>findById(Long postId);
    Page<Post> findAll(Pageable pageable);
    
    Optional<Post> findByMemberId(Long memberId);
    Page<Post> findAllByMemberId(Long memberId, Pageable pageable);
    Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query("select new com.sparta.soomtut.dto.response.PostResponseDto(p.image,p.fee,l.address,m.nickname,p.content,p.title) from Post p join Member m on p.member.id = m.id join Location l on l.member.id=m.id where p.content LIKE :keyword%")
    Page<PostResponseDto> findPostByKeyword(String keyword, Pageable pageable);
}
