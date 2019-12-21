package Lib;

import MapElements.Animal;

public interface IPositionChangeObserver {
  public void positionChanged(Animal animal, Vector2d oldPosition);
}
