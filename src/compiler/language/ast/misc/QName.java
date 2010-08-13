package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.Name;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class QName
{

  private ParseInfo parseInfo;

  private Name[] names;

  /**
   * Creates a new QName containing only one name
   * @param startName - the name to start the QName with
   * @param parseInfo - the parsing information
   */
  public QName(Name startName, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    names = new Name[] {startName};
  }

  /**
   * Creates a new QName based on an old QName, but with an additional name
   * @param original - the QName to base this QName on
   * @param additional - the extra name to add
   * @param parseInfo - the parsing information
   */
  public QName(QName original, Name additional, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    Name[] origNames = original.names;
    names = new Name[origNames.length + 1];
    System.arraycopy(origNames, 0, names, 0, origNames.length);
    names[origNames.length] = additional;
  }

  /**
   * Creates a new QName with the specified list of names.
   * @param names - the names to include in this QName
   * @param parseInfo - the parsing information
   */
  public QName(Name[] names, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.names = names;
  }

  /**
   * @return the names
   */
  public Name[] getNames()
  {
    return names;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < names.length; i++)
    {
      buffer.append(names[i]);
      if (i != names.length - 1)
      {
        buffer.append(".");
      }
    }
    return buffer.toString();
  }
}
