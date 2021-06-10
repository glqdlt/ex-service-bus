package coms.glqdlt.ex.springevent;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * @author glqdlt
 */
public class EventBase extends ApplicationEvent {

    public static String makeId() {
        return UUID.randomUUID().toString();
    }

    private String label;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public EventBase(String source, String label, String id) {
        super(source);
        this.label = label;
        this.id = id;
    }

    @Override
    public String toString() {
        return "EventBase{" +
                "label='" + label + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
