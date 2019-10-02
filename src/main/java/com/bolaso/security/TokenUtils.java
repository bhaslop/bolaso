package com.bolaso.security;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TokenUtils {
    private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    public static String claimsAsJson(Map<String, Claim> claims) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        claims.forEach((key, value)-> {
            if( value.asMap() != null ) {
                node.putPOJO(key, value.asMap());
            } else if( value.asList(String.class) != null ) {
                JsonNode jsonNode = objectMapper.valueToTree(value.asList(String.class));
                node.set(key, jsonNode);
            } else if( value.asBoolean() != null ) {
                node.put(key, value.asBoolean());
            } else if( value.asInt() != null ) {
                node.put(key, value.asInt());
            } else if( value.as(String.class) != null ) {
                node.put(key, value.as(String.class));
            } else if (value.isNull() ) {
                 node.putNull(key);
            }
        });


        String json;

        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (JsonProcessingException e) {
            logger.error("Error processing json from profile", e);
            json = "";
        }

        return json;
    }
}
