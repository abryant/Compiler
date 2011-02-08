package x;

immutable sealed class String
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
