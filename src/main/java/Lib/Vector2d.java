package Lib;

public class Vector2d {
  public final int x;
  public final int y;

  public Vector2d(int x, int y){
    this.x = x;
    this.y = y;
  }

  public String toString(){
    return new StringBuilder("(").append(this.x).append(",")
                .append(this.y).append(")").toString();
  }

  public boolean precedes(Vector2d other){
    return this.x <= other.x && this.y <= other.y;
  }

  public boolean follows(Vector2d other){
    return this.x >= other.x && this.y >= other.y;
  }

  public Vector2d add(Vector2d other){
    Vector2d vector2d = new Vector2d(this.x + other.x, this.y + other.y);
    return vector2d;
  }

  public boolean equals(Object other){
    if (this == other)
      return true;
    if (!(other instanceof Vector2d))
      return false;
    Vector2d that = (Vector2d) other;
    if(this.x == that.x && this.y == that.y)
      return true;
    return false;
  }

  @Override
  public int hashCode(){
    int hash = 177;
    hash += x * 89;
    hash += y * 62;
    return hash;
  }

  public Vector2d opposite(){
    Vector2d vector2d = new Vector2d(-this.x, -this.y);
    return vector2d;
  }
}
