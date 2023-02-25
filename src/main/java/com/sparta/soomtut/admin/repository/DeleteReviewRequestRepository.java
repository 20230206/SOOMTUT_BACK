package com.sparta.soomtut.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.admin.entity.DeleteReviewRequest;

public interface DeleteReviewRequestRepository extends JpaRepository<DeleteReviewRequest,Long> {
}
