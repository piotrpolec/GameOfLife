package Maps;

import Lib.Vector2d;
import MapElements.Animal;

public interface IMap {
  boolean place(Animal animal);
  boolean isOccupied(Vector2d position);
  Object objectAt(Vector2d position);
}
