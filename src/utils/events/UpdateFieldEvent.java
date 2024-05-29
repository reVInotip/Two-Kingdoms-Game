package utils.events;

import model.AbstractUnit;

import java.util.List;

public record UpdateFieldEvent(List<AbstractUnit> allUnisInField, List<Integer> playerResources) implements Event {
}
