package com.emorinken.review.service.impl;

import com.emorinken.review.domain.Review;
import com.emorinken.review.repository.ReviewRepository;
import com.emorinken.review.service.ReviewService;
import com.emorinken.review.service.dto.ReviewDTO;
import com.emorinken.review.service.mapper.ReviewMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.emorinken.review.domain.Review}.
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        LOG.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDTO update(ReviewDTO reviewDTO) {
        LOG.debug("Request to update Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public Optional<ReviewDTO> partialUpdate(ReviewDTO reviewDTO) {
        LOG.debug("Request to partially update Review : {}", reviewDTO);

        return reviewRepository
            .findById(reviewDTO.getId())
            .map(existingReview -> {
                reviewMapper.partialUpdate(existingReview, reviewDTO);

                return existingReview;
            })
            .map(reviewRepository::save)
            .map(reviewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        LOG.debug("Request to get all Reviews");
        return reviewRepository.findAll().stream().map(reviewMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> findOne(Long id) {
        LOG.debug("Request to get Review : {}", id);
        return reviewRepository.findById(id).map(reviewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Review : {}", id);
        reviewRepository.deleteById(id);
    }
}
