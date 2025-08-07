package com.az.software.checkers.config;

import com.az.software.checkers.domain.model.Position;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Handles either:
 * • JSON object: { "row":5, "col":0 }
 * • String: "B6"
 */
public class PositionDeserializer extends JsonDeserializer<Position> {

    @Override
    public Position deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            // e.g. "B6"
            String alg = node.textValue();
            return Position.fromAlgebraic(alg);
        } else if (node.isObject()) {
            // { "row": 5, "col": 0 }
            JsonNode r = node.get("row");
            JsonNode c = node.get("col");
            if (r == null || c == null || !r.isInt() || !c.isInt()) {
                throw JsonMappingException.from(p,
                        "Position object must have integer row and col fields");
            }
            return new Position(r.intValue(), c.intValue());
        }

        throw JsonMappingException.from(p,
                "Cannot deserialize Position from " + node);
    }

}