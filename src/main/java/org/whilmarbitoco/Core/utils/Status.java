package org.whilmarbitoco.Core.utils;

import jakarta.ws.rs.core.Response;

import java.util.Map;

public class Status {

    public static Response ok(String message) {
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("result", message))
                .build();
    }
}
