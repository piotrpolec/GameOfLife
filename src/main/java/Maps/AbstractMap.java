package Maps;

import Lib.IPositionChangeObserver;
import Lib.Vector2d;
import MapElements.Animal;
import MapElements.Grass;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap implements IMap, IPositionChangeObserver{
  private Multimap<Vector2d, Animal> animalsMap = ArrayListMultimap.create();
  protected Map<Vector2d, Grass> grassHashMap = new HashMap<>();

  public Map<Vector2d, Grass> getGrassHashMap() {
    return grassHashMap;
  }

  public Multimap<Vector2d, Animal> getAnimalsMap() {
    return animalsMap;
  }

  @Override
  public boolean place(Animal animal) {
    animalsMap.put(animal.getPosition(), animal);
    animal.addObserver(this);
    return true;
  }

  public boolean isGrassy(Vector2d position) {
    return grassHashMap.get(position) != null;
  }

  public Object objectAt(Vector2d position) {
    if(animalsMap.get(position).size() == 0) return grassHashMap.get(position);
    else return animalsMap.get(position);
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    return objectAt(position) != null;
  }

  public void removeAnimal(Animal animal) {
    animalsMap.remove(animal.getPosition(), animal);
  }

  @Override
  public void positionChanged(Animal animal, Vector2d oldPosition){
    animalsMap.remove(oldPosition, animal);
    animalsMap.put(animal.getPosition(), animal);
  }

  public abstract String toString();
}
