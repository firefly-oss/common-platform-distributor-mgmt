package com.catalis.core.distributor.core.mappers;

import com.catalis.core.distributor.interfaces.dtos.ProductDTO;
import com.catalis.core.distributor.models.entities.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for converting between Product entity and ProductDTO.
 */
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Convert a Product entity to a ProductDTO.
     *
     * @param product the Product entity
     * @return the ProductDTO
     */
    @Mapping(source = "specifications", target = "specifications", qualifiedByName = "stringToJsonNode")
    public abstract ProductDTO toDTO(Product product);

    /**
     * Convert a ProductDTO to a Product entity.
     *
     * @param productDTO the ProductDTO
     * @return the Product entity
     */
    @Mapping(source = "specifications", target = "specifications", qualifiedByName = "jsonNodeToString")
    public abstract Product toEntity(ProductDTO productDTO);

    /**
     * Convert a JSON string to a JsonNode.
     *
     * @param json the JSON string
     * @return the JsonNode
     */
    @Named("stringToJsonNode")
    protected JsonNode stringToJsonNode(String json) {
        if (json == null || json.isEmpty()) {
            return objectMapper.createObjectNode();
        }
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            return objectMapper.createObjectNode();
        }
    }

    /**
     * Convert a JsonNode to a JSON string.
     *
     * @param jsonNode the JsonNode
     * @return the JSON string
     */
    @Named("jsonNodeToString")
    protected String jsonNodeToString(JsonNode jsonNode) {
        if (jsonNode == null) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}