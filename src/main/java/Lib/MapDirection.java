package Lib;

import java.util.Random;

public enum MapDirection {
  NORTH,EAST,SOUTH,WEST,NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

  public static MapDirection randomDirection(){
    return values()[new Random().nextInt(values().length)];
  }

  public MapDirection next(){
    switch(this){
      case EAST: return SOUTHEAST;
      case SOUTHEAST: return SOUTH;
      case SOUTH: return SOUTHWEST;
      case SOUTHWEST: return WEST;
      case WEST: return  NORTHWEST;
      case NORTHWEST: return NORTH;
      case NORTH: return NORTHEAST;
      case NORTHEAST: return EAST;
    }
    return null;
  }

  public MapDirection next(int degrees){
    if(degrees == 0) return this;
    else return this.next().next(degrees-45);
  }

  public MapDirection previous(){
    switch(this){
      case EAST: return NORTHEAST;
      case NORTHEAST: return NORTH;
      case NORTH: return NORTHWEST;
      case NORTHWEST: return WEST;
      case WEST: return  SOUTHWEST;
      case SOUTHWEST: return SOUTH;
      case SOUTH: return SOUTHEAST;
      case SOUTHEAST: return EAST;
    }
    return null;
  }

  public Vector2d toUnitVector(){
    int x;
    int y;
    switch(this){
      case NORTH: x = 0; y = 1; break;
      case NORTHEAST: x = 1; y = 1; break;
      case EAST: x = 1; y = 0; break;
      case SOUTHEAST: x = 1; y = -1; break;
      case SOUTH: x = 0; y = -1; break;
      case SOUTHWEST: x = -1; y = -1; break;
      case WEST: x = -1; y = 0; break;
      case NORTHWEST: x = -1; y = 1; break;
      default: x = 0; y = 0;
    }
    Vector2d vector2d = new Vector2d(x,y);
    return vector2d;
  }
}
