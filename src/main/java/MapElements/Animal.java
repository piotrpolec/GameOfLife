package MapElements;

import Lib.Genes;
import Lib.IPositionChangeObserver;
import Lib.MapDirection;
import Lib.Vector2d;
import Maps.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {
  private MapDirection direction;
  private Vector2d position;
  private final WorldMap map;
  private List<IPositionChangeObserver> observers = new ArrayList<>();
  private final Random generator = new Random();
  private int energy;
  private static int moveEnergy;
  private Genes genes;

  public Animal(WorldMap map, Vector2d initialPosition, Integer initialEnergy, int moveEnergy ){
    this.map = map;
    this.position = initialPosition; //zdebugowac jesli niepoprawna
    this.direction = MapDirection.randomDirection();
    this.energy = initialEnergy;
    this.moveEnergy = moveEnergy;
    this.genes = new Genes();
  }

  public Animal(WorldMap map, Vector2d initialPosition, int initialEnergy, int moveEnergy, Genes genes){
    this(map, initialPosition, initialEnergy, moveEnergy);
    this.genes = genes;
  }

  @Override
  public Vector2d getPosition() {
    return position;
  }

  public int getEnergy() {
    return energy;
  }

  public String toString() {
    return 	"\ud83d\udc2e";
  } // zmienic"\\ud83d\\udc2e"

  public void move(){
    this.rotate();
    Vector2d oldPosition = this.getPosition();
    Vector2d newPosition = this.position.add(this.direction.toUnitVector());

    if(map.isNotViable(newPosition)){
      newPosition = map.wrapAround(newPosition);
    }
    this.position = newPosition;
    this.changeEnergy(-moveEnergy);
    this.positionChanged(this, oldPosition);
  }

  public void rotate(){
    int degrees = this.genes.rotation();
    MapDirection newDirection = this.direction.next(degrees);
    this.direction = newDirection;
  }

  public boolean eat(int plantEnergy){
    if(map.isGrassy(position)){
      map.getGrassHashMap().remove(this.getPosition());
      this.changeEnergy(plantEnergy);
      return true;
    }
    return false;
  }

  public void eatTogether(Integer numberOfAnimals){
    this.changeEnergy((int)Math.floor(5/numberOfAnimals));
  }

  public void changeEnergy(int value){
    energy += value;
  }

  public boolean reproduce(Animal other, int initialEnergy) {
    if(this.energy > initialEnergy/2 && other.energy > initialEnergy/2) {
      int [] breakpoints = getBreakpoints();
      Vector2d kidPosition = this.map.getPositionAround(this.getPosition());
      Animal child = new Animal(this.map, kidPosition, this.energy/4 + other.energy/4,
          moveEnergy, new Genes(breakpoints[0],breakpoints[1], this.genes, other.genes));
      this.map.place(child);
      this.changeEnergy(-this.energy/4);
      other.changeEnergy(-this.energy/4);
      return true;
    }
    return false;
  }

  public void die(){
    IPositionChangeObserver[] observers = this.observers.toArray(new IPositionChangeObserver[this.observers.size()] );
    for(IPositionChangeObserver observer:observers){
      this.removeObserver(observer);
    }
    this.map.removeAnimal(this);
  }

  private int[] getBreakpoints() {
    int x = generator.nextInt(30);
    int y = generator.nextInt(30);
    while(x == y) y = generator.nextInt(30);
    int [] breakpoints = {x,y};
    return  breakpoints;
  }
  public void addObserver(IPositionChangeObserver observer) {
    this.observers.add(observer);
  }

  public void removeObserver(IPositionChangeObserver observer){
    this.observers.remove(observer);
  }

  private void positionChanged(Animal animal, Vector2d oldPosition) {
    for(IPositionChangeObserver observer:observers){
      observer.positionChanged(animal, oldPosition);
    }
  }
}
