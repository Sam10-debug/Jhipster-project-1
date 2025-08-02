package com.emorinken.review.service.mapper;

import com.emorinken.review.domain.Review;
import com.emorinken.review.service.dto.ReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Review} and its DTO {@link ReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {}
