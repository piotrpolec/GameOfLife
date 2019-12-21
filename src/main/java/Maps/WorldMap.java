package Maps;

import Lib.Vector2d;
import MapElements.Grass;
import Visualizers.MapVisualizer;

import java.util.Random;

public class WorldMap extends AbstractMap {
  private final MapVisualizer mapVisualizer = new MapVisualizer(this);
  private  Vector2d upperRight;
  private  Vector2d lowerLeft;
  private  Vector2d jungleUpperRight;
  private  Vector2d jungleLowerLeft;
  private final Random generator = new Random();

  public WorldMap(int height, int width, double jungleRatio){
    upperRight = new Vector2d(width/2,height/2);
    lowerLeft = new Vector2d(- width/2,- height/2);
    jungleUpperRight = new Vector2d((int) Math.ceil(width*jungleRatio/2),(int) Math.ceil(height*jungleRatio/2));
    jungleLowerLeft = new Vector2d((int) Math.ceil(-width*jungleRatio/2),(int) Math.ceil(-height*jungleRatio/2));
  }

  public Vector2d getRandomPosition(){
    Vector2d position = new Vector2d(generator.nextInt(2*upperRight.x + 1)-upperRight.x,
        generator.nextInt(2*upperRight.y + 1)-upperRight.y);
    while (objectAt(position)!= null) position = new Vector2d(generator.nextInt(2 * upperRight.x + 1)-upperRight.x,
        generator.nextInt(2 * upperRight.y + 1)-upperRight.y);
    return position;
  }

  public boolean isNotViable(Vector2d position) {
    if(!(position.follows(lowerLeft) && position.precedes(upperRight))) return true;
    return false;
  }

  public Vector2d wrapAround(Vector2d position){
    if(position.x > upperRight.x) position = position.add(new Vector2d(lowerLeft.x * 2 - 1,0));
    if(position.y > upperRight.y) position = position.add(new Vector2d(0, lowerLeft.y * 2 - 1));
    if(position.x < lowerLeft.x)  position = position.add(new Vector2d(upperRight.x * 2 + 1, 0));
    if(position.y < lowerLeft.y) position = position.add(new Vector2d(0, upperRight.y * 2 + 1));
    return position;
  }

  public Vector2d getPositionAround(Vector2d position) {
    if(getFreePositionAround(position) == null) return getRandomPositionAround(position);
    else return getFreePositionAround(position);
  }

  private Vector2d getFreePositionAround(Vector2d position){
    Vector2d freePosition = null;
    for(int x = -1; x < 2; x++) {
      for(int y = -1; y < 2; y++ ) {
        if(!isOccupied(position.add(new Vector2d(x,y)))) {
          freePosition = position.add(new Vector2d(x, y));
        }
      }
    }
    return freePosition;
  }

  private Vector2d getRandomPositionAround(Vector2d position) {
    return position.add(new Vector2d(generator.nextInt(2)-1, generator.nextInt(2)-1));
  }

  public void growGrass(Vector2d position){
    Grass grass = new Grass(position);
    grassHashMap.put(position, grass);
  }

  public boolean growGrassInSteppe(){
    if(isSteppeFull()) return false;
    while(true){
      Vector2d position = getRandomPosition();
      if(isInSteppe(position) && !isOccupied(position)){
        growGrass(position);
        return true;
      }
    }
  }

  public boolean growGrassInJungle(){
    if(isJungleFull()) return false;
    while(true){
      Vector2d position = getRandomPosition();
      if(isInJungle(position) && !isOccupied(position)){
        growGrass(position);
        return true;
      }
    }
  }

  private boolean isJungleFull(){
    for(int x = jungleLowerLeft.x; x < jungleUpperRight.x; x++)
      for(int y = jungleLowerLeft.y; y < jungleUpperRight.y; y++)
        if (!isOccupied(new Vector2d(x,y))) return false;

    return true;
  }

  private boolean isSteppeFull(){
    for(int x = lowerLeft.x; x < upperRight.x; x++)
      for(int y = lowerLeft.y; y < upperRight.y; y++){
        if(isInJungle(new Vector2d(x,y))) continue;
        if(!isOccupied(new Vector2d(x,y))) return false;
      }

    return true;
  }

  private boolean isInSteppe(Vector2d position){
    if(isInJungle(position)) return false;
    return position.follows(lowerLeft) && position.precedes(upperRight);
  }

  private boolean isInJungle(Vector2d position) {
    return position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight);
  }

  @Override
  public String toString() {
    return mapVisualizer.draw(lowerLeft, upperRight);
  }
}
