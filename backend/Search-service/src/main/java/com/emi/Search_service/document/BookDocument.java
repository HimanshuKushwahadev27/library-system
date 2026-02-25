package com.emi.Search_service.document;


import java.util.List;


import org.springframework.data.annotation.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName= "books")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDocument {

    @Id
    private String id;   

    @Field(type = FieldType.Keyword)
    private String bookId;  

    @Field(
        type = FieldType.Text,
        analyzer = "standard"   
    )
    private String title;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Double)
    private Double price;   

    @Field(type = FieldType.Text)
    private List<String> authorName;

    @Field(type = FieldType.Keyword)
    private List<String> genreName;

    @Field(type = FieldType.Boolean)
    private Boolean freePreview;

    @Field(type = FieldType.Date)
    private Long publishedAt;
    
    @Field(type = FieldType.Date)
    private Long updatedAt;
    
    @Field(type = FieldType.Keyword)
    private String lifeCycleStatus;
    
    @Field(type = FieldType.Keyword)
    private String visibilityStatus;
}


