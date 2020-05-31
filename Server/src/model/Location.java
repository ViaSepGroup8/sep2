package model;

import java.io.Serializable;
import java.util.Arrays;

public class Location implements Serializable
{
  String A;
  Integer B;
  Integer C;

  public Location(String A, Integer B, Integer C)
  {
    this.A = A;
    this.B = B;
    this.C = C;
  }
  public static Location fromString(String s){
    String []myArray = new String[2];
    myArray = s.split ("");
    return new Location (myArray[0],Integer.parseInt (myArray[1]),Integer.parseInt (myArray[2]));
  }
  public boolean onRightSide()
  {
    return B % 2 == 0;
  }

  public String getA()
  {
    return A;
  }

  public Integer getB()
  {
    return B;
  }

  public Integer getC()
  {
    return C;
  }

  public String codeFormat()
  {
    return String.format("%s-%02d-%02d",A, B, C);
  }

  public String readableFormat()
  {
    return String.format("In ale: %s on position: 02%d:02%d", A, B, C);
  }

  @Override public String toString()
  {
    return codeFormat();
  }
}