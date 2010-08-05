package compiler.language.ast.terminal;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class Name extends Terminal
{

  private String name;

  /**
   * Creates a Name with the specified text and position in the file
   * @param name - the text of the name
   * @param line - the line number
   * @param startPos - the starting position of the Name on the specified line
   * @param length - the length of the name
   */
  public Name(String name, int line, int startPos, int length)
  {
    super(line, startPos, length);
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return name;
  }
}
