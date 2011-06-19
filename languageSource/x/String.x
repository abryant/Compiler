package x;

import y.*;

interface Bar
{
}

immutable sealed class String extends Foo<String>.Bar implements Bar
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
