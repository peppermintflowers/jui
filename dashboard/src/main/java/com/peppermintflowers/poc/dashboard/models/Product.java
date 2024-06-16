package com.peppermintflowers.poc.dashboard.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document("product_catalogue")
@NoArgsConstructor
@Getter
@Setter
public class Product implements Serializable {
    @Field("id")
    Long id;
    @Field("price")
    Long price;
    @Field("styleType")
    String styleType;
    @Field("articleNumber")
    String articleNumber;
    @Field("productDisplayName")
    String productDisplayName;
    @Field("variantName")
    String variantName;
    @Field("myntraRating")
    Long myntraRating;
    @Field("brandName")
    String brandName;
    @Field("ageGroup")
    String ageGroup;
    @Field("gender")
    String gender;
    @Field("baseColour")
    String baseColour;
    @Field("colour1")
    String colour1;
    @Field("colour2")
    String colour2;
    @Field("fashionType")
    String fashionType;
    @Field("season")
    String season;
    @Field("usage")
    String usage;
    @Field("displayCategories")
    String displayCategories;
    @Field("masterCategory.typeName")
    String masterCategory;
    @Field("subCategory.typeName")
    String subCategory;
    @Field("articleType.typeName")
    String articleType;
    @Field("description.value")
    String descriptionValue;
    @Field("styleImages.default.imageURL")
    String defaultImageUrl;
    @Field("styleImages.front.imageURL")
    String frontImageUrl;
    @Field("styleImages.back.imageURL")
    String backImageUrl;
    @Field("styleOptions")
    StyleOption[] options;
}
