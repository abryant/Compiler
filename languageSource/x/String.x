package x;

interface Foo
{
}

interface Bar extends Foo
{
}

immutable sealed class String extends Object implements Bar
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
