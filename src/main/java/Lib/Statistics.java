package Lib;

public class Statistics {
  private int animalsCount = 0;
  private int animalsBornCount = 0;
  private int deadAnimalsCount = 0;
  private int grassCount = 0;
  private int grassEatenCount = 0;
  private int daysPassed = 0;

  public Statistics() {}

  public void changeAnimalsCount(int value){
    animalsCount += value;
  }

  public void addAnimalBorn(){
    animalsBornCount += 1;
    animalsCount += 1;
  }

  public void addDeadAnimal(){
    deadAnimalsCount += 1;
    animalsCount -= 1;
  }

  public void changeGrassCount(int value){
    grassCount += value;
  }

  public void addGrassEaten(){
    grassEatenCount += 1;
    grassCount -= 1;
  }

  public void addDay(){
    daysPassed += 1;
  }
  public String toString(){
    String stats = new StringBuilder("Stats:").append(System.lineSeparator())
        .append("Days passed: ").append(daysPassed).append(System.lineSeparator())
        .append("Animals on map: ").append(animalsCount).append(System.lineSeparator())
        .append("Animals born count: ").append(animalsBornCount).append(System.lineSeparator())
        .append("Dead animals count: ").append(deadAnimalsCount).append(System.lineSeparator())
        .append("Grass on map: ").append(grassCount).append(System.lineSeparator())
        .append("Grass eaten: ").append(grassEatenCount).append(System.lineSeparator())
        .toString();
    return stats;
  }
}
