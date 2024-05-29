package utils;

import utils.events.Event;

public interface Observer {
    void update(Event event);
}
