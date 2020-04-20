package model;

public class test
{
  public static void main(String[] args)
  {
    Location loko = new Location("A32", 2, 3);
    System.out.println(loko.codeFormat());
    System.out.println(loko.readableFormat());
  }
}
