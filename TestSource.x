package x.lang.String;

/**
 * A String class, which holds an array of characters.
 */
immutable class String
{
  property char[] chars public retrieve private assign;
  
  String(char[] chars)
  {
    this.chars = chars;
  }
  
  /**
   * Copy constructor
   */
  String(String original)
  {
    chars = original.chars;
  }
  
  int indexOf(char c)
  {
    for int i = 0; i < chars.length; i++
    {
      if chars[i] == c
      {
        return i;
      }
      return -1;
    }
  }
  
  String substring(int @begin = 0, int @end = chars.length)
  {
    if begin < 0 || begin > end || end > chars.length
    {
      throw new IndexOutOfBoundsException();
    }
    char[] newChars = new char[end - begin];
    for int i = 0; i < end - begin; i++
    {
      newChars[i] = chars[begin + i];
    }
    return new String(newChars);
  }
  
  String toString()
  {
    return this;
  }
  
  static void main(String... args)
  {
    String str = new String("test\n");
    System.out.print(str + '\uabcd');
  }
}
