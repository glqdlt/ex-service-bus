package com.glqdlt.ex.servicebusexample.topic;

import java.util.UUID;

/**
 * @author glqdlt
 */
public class DefaultEvent implements EventIdentity {

    private String id;
    private String description;
    private String writer;

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static DefaultEvent valueOf(String writer,
                                       String description) {
        DefaultEvent d
                = new DefaultEvent();
        d.setId(UUID.randomUUID().toString());
        d.setDescription(description);
        d.setWriter(writer);
        return d;
    }
}
