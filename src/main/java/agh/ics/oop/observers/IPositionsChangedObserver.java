package agh.ics.oop.observers;

import agh.ics.oop.Vector2d;

import java.util.List;

public interface IPositionsChangedObserver {
    void positionsChanged(List<Vector2d> positions);
}
