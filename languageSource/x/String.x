package x;

import y.*;

interface Bar
{
}

public immutable sealed class String extends Foo<String> implements Bar
{
  final property char#[] chars
  private setter
  public getter;
  
  String(char[] chars)
  {
    this.chars = chars;
  }
  
  String(String other)
  {
    this.chars = other.chars;
  }
  
  String toString()
  {
    return this;
  }
}
