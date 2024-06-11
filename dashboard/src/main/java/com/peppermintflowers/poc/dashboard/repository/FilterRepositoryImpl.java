package com.peppermintflowers.poc.dashboard.repository;

import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FilterRepositoryImpl implements FilterRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public List<Product> findByProductFilters(ProductFilters productFilters) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        Query query = new Query();
        if(productFilters.getDiscountedPrice() != null){
            criterias.add(Criteria.where("discountedPrice").lte(productFilters.getDiscountedPrice()));
        }
        if(productFilters.getProductDisplayName() != null){
            criterias.add(Criteria.where("productDisplayName").is(productFilters.getProductDisplayName()));
        }
        if(productFilters.getMyntraRating() != null){
            criterias.add(Criteria.where("myntraRating").gte(productFilters.getMyntraRating()));
        }
        if(productFilters.getBrandName() != null){
            criterias.add(Criteria.where("brandName").is(productFilters.getBrandName()));
        }
        if(productFilters.getAgeGroup() != null){
            criterias.add(Criteria.where("ageGroup").is(productFilters.getAgeGroup()));
        }
        if(productFilters.getGender() != null){
            criterias.add(Criteria.where("gender").is(productFilters.getGender()));
        }
        if(productFilters.getSeason() != null){
            criterias.add(Criteria.where("season").is(productFilters.getSeason()));
        }
        if(productFilters.getUsage() != null){
            criterias.add(Criteria.where("usage").is(productFilters.getUsage()));
        }
        if(productFilters.getDisplayCategories() != null){
            criterias.add(Criteria.where("displayCategories").is(productFilters.getDisplayCategories()));
        }
        if(productFilters.getArticleType() != null){
            criterias.add(Criteria.where("articleType").is(productFilters.getArticleType()));
        }
        if(productFilters.getSubCategory() != null){
            criterias.add(Criteria.where("subCategory").is(productFilters.getSubCategory()));
        }
        if(productFilters.getMasterCategory() != null){
            criterias.add(Criteria.where("masterCategory").is(productFilters.getMasterCategory()));
        }
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Product.class);

    }
}
