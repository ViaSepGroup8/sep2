package model;

public class Location
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
    return String.format("%s-02%d-02%d", B, C);
  }

  public String readableFormat()
  {
    return String.format("In ale: %s on position: 02%d:02%d", A, B, C);
  }
}