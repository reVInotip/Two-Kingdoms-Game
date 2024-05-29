package utils.events;

import java.util.List;

public record UpdateResourcesEvent(List<Integer> playerResources) implements Event {
}
