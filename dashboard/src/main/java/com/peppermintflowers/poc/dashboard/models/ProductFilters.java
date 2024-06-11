package com.peppermintflowers.poc.dashboard.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Setter
@Getter
@AllArgsConstructor
public class ProductFilters {

    Long id;

    Long discountedPrice;

    String productDisplayName;

    Long myntraRating;

    String brandName;

    String ageGroup;

    String gender;

    String season;

    String usage;

    String displayCategories;

    String masterCategory;

    String subCategory;

    String articleType;
}
