package Game;

import Lib.Statistics;
import Lib.Vector2d;
import MapElements.Animal;
import Maps.WorldMap;
import com.google.common.collect.Iterables;

import java.util.Arrays;
import java.util.Collection;

public class World {
  private WorldMap map;
  private int initialEnergy;
  private int plantEnergy;
  private int moveEnergy;
  private Statistics statistics;

  public World(int height, int width, float jungleRatio, int initialEnergy, int moveEnergy, int plantEnergy, int animalsCount) {
    map = new WorldMap(height, width, jungleRatio);
    this.initialEnergy = initialEnergy;
    this.plantEnergy = plantEnergy;
    this.moveEnergy = moveEnergy;
    this.statistics = new Statistics();
    spawnAnimals(animalsCount);
  }

  private void spawnAnimals(int animalsCount) {
    int i = 0;
    while (i < animalsCount) {
      if (map.place(new Animal(map, map.getRandomPosition(), initialEnergy, moveEnergy))) i++;
    }
    statistics.changeAnimalsCount(animalsCount);
  }

  public void passTheDay() {
    removeDeadAnimals();
    moveAnimals();
    eatAndReproduce();
    dailyGrassGrow();
    statistics.addDay();
  }
  private void removeDeadAnimals(){
    Collection<Animal> animalCollection = map.getAnimalsMap().values();
    Animal[] animals = new Animal[animalCollection.size()];
    animals = animalCollection.toArray(animals);
    for (Animal animal : animals) {
      if(animal.getEnergy() <= 0) {
        animal.die();
        statistics.addDeadAnimal();
      }
    }
  }

  private void moveAnimals() {
    Collection<Animal> animalCollection = map.getAnimalsMap().values();
    Animal[] animals = new Animal[animalCollection.size()];
    animals = animalCollection.toArray(animals);
    for (Animal animal : animals)  animal.move();
  }

  private void eatAndReproduce(){
    Vector2d[] positions = this.map.getAnimalsMap().keySet().toArray(new Vector2d[this.map.getAnimalsMap().keySet().size()]);
    for (Vector2d position : positions) {
      Collection<Animal> animalsAtPosition = this.map.getAnimalsMap().get(position);
      if (animalsAtPosition.size() == 1) {
        Animal animal = Iterables.getOnlyElement(animalsAtPosition);
        if(animal.eat(plantEnergy)) statistics.addGrassEaten();
      } else {
        Animal[] animalsArrayAtPosition = animalsAtPosition.toArray(new Animal[animalsAtPosition.size()]);
        Arrays.sort(animalsArrayAtPosition, (animal1, animal2) -> animal2.getEnergy() - animal1.getEnergy());

        if (countEquallyStrongAnimals(animalsArrayAtPosition) > 1)
          divideMeal(Arrays.copyOfRange(animalsArrayAtPosition, 0, countEquallyStrongAnimals(animalsArrayAtPosition) - 1));
        else if(animalsArrayAtPosition[0].eat(plantEnergy)) {
          statistics.addGrassEaten();
        }

        if(animalsArrayAtPosition[0].reproduce(animalsArrayAtPosition[1], initialEnergy)) statistics.addAnimalBorn();
      }
    }
  }

  private void divideMeal(Animal[] strongestAnimals) {
    if(map.isGrassy(strongestAnimals[0].getPosition())) {
      for (Animal animal : strongestAnimals) animal.eatTogether(strongestAnimals.length);{
        map.getGrassHashMap().remove(strongestAnimals[0].getPosition());
      }
      statistics.addGrassEaten();
    }
  }

  private int countEquallyStrongAnimals(Animal[] sortedAnimals) {
    int highestEnergy = sortedAnimals[0].getEnergy();
    int equallyStrongAnimalsCounter = 0;
    for (Animal animal : sortedAnimals) {
      if (animal.getEnergy() == highestEnergy) equallyStrongAnimalsCounter += 1;
      else return equallyStrongAnimalsCounter;
    }
    return equallyStrongAnimalsCounter;
  }

  private void dailyGrassGrow() {
    if(map.growGrassInSteppe()) statistics.changeGrassCount(1);
    if(map.growGrassInJungle()) statistics.changeGrassCount(1);
  }

  public boolean animalsExtinct() {
    return map.getAnimalsMap().size() == 0;
  }

  @Override
  public String toString() {
    return  new StringBuilder(map.toString()).append(statistics.toString()).toString();
  }
}
