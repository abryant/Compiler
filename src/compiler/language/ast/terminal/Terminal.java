package compiler.language.ast.terminal;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class Terminal
{

  private int line;
  private int startPos;
  private int length;

  /**
   * Creates a new Terminal with the specified line number, start position on that line, and length
   * @param line - the line number
   * @param startPos - the starting position of the terminal on the specified line
   * @param length - the length of the terminal
   */
  public Terminal(int line, int startPos, int length)
  {
    this.line = line;
    this.startPos = startPos;
    this.length = length;
  }

  /**
   * @return the line number
   */
  public int getLine()
  {
    return line;
  }

  /**
   * @return the start position of the terminal on its line
   */
  public int getStartPos()
  {
    return startPos;
  }

  /**
   * @return the length of the terminal
   */
  public int getLength()
  {
    return length;
  }

}
