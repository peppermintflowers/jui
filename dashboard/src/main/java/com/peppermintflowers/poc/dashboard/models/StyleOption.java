package com.peppermintflowers.poc.dashboard.models;

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
public class StyleOption implements Serializable {
    @Field("skuId")
    Long skuId;
    @Field("unifiedSize")
    String unifiedSize;
}
