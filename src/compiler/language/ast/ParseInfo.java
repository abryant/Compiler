package compiler.language.ast;

/*
 * Created on 5 Aug 2010
 */

/**
 * An immutable class which stores information from the parser, such as file location
 * @author Anthony Bryant
 */
public class ParseInfo
{

  private int startLine;
  private int startPos;
  private int endLine;
  private int endPos;

  /**
   * Creates a new ParseInfo object containing the specified start and end positions
   * @param startLine - the start line
   * @param startPos - the starting position on the start line
   * @param endLine - the end line
   * @param endPos - the ending position on the end line
   */
  public ParseInfo(int startLine, int startPos, int endLine, int endPos)
  {
    this.startLine = startLine;
    this.startPos = startPos;
    this.endLine = endLine;
    this.endPos = endPos;
  }

  /**
   * Creates a new ParseInfo object containing the start and end position info from the specified ParseInfo objects
   * @param parseInfo - the ParseInfo objects to combine
   * @return a ParseInfo object representing all of the specified ParseInfo objects, or null if all specified inputs are null
   */
  public static ParseInfo combine(ParseInfo... parseInfo)
  {
    ParseInfo startInfo = null;
    ParseInfo endInfo = null;
    for (ParseInfo info : parseInfo)
    {
      if (startInfo == null)
      {
        startInfo = info;
      }
      if (info != null)
      {
        endInfo = info;
      }
    }
    if (startInfo == null)
    {
      // all ParseInfo objects were null, so return a null ParseInfo
      return null;
    }
    return new ParseInfo(startInfo.getStartLine(), startInfo.getStartPos(), endInfo.getEndLine(), endInfo.getEndPos());
  }

  /**
   * @return the startLine
   */
  public int getStartLine()
  {
    return startLine;
  }

  /**
   * @return the startPos
   */
  public int getStartPos()
  {
    return startPos;
  }

  /**
   * @return the endLine
   */
  public int getEndLine()
  {
    return endLine;
  }

  /**
   * @return the endPos
   */
  public int getEndPos()
  {
    return endPos;
  }

}
