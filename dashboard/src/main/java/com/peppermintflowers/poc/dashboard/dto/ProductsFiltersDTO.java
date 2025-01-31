package com.peppermintflowers.poc.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class ProductsFiltersDTO {

    Long id;

    Long price;

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

    public boolean isEmpty() {
        if(id != null){
            return false;
        }
        if(price != null){
            return false;
        }
        if(productDisplayName != null && !productDisplayName.isEmpty()){
            return false;
        }
        if(myntraRating != null){
            return false;
        }
        if(brandName != null && !brandName.isEmpty()){
            return false;
        }
        if(ageGroup != null && !ageGroup.isEmpty()){
            return false;
        }
        if(gender !=null && !gender.isEmpty()){
            return false;
        }
        if(season !=null && !season.isEmpty()){
            return false;
        }
        if(usage !=null && !usage.isEmpty()){
            return false;
        }
        if(displayCategories != null && !displayCategories.isEmpty()){
            return false;
        }
        if(masterCategory != null && !masterCategory.isEmpty()){
            return false;
        }
        if(subCategory != null && !subCategory.isEmpty()){
            return false;
        }
        if(articleType != null && articleType.isEmpty()){
            return false;
        }
        return true;
    }
}
