package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class QNameAST
{

  private ParseInfo parseInfo;

  private NameAST[] names;

  /**
   * Creates a new QNameAST containing only one name
   * @param startName - the name to start the QNameAST with
   * @param parseInfo - the parsing information
   */
  public QNameAST(NameAST startName, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    names = new NameAST[] {startName};
  }

  /**
   * Creates a new QNameAST based on an old QNameAST, but with an additional name
   * @param original - the QNameAST to base this QNameAST on
   * @param additional - the extra name to add
   * @param parseInfo - the parsing information
   */
  public QNameAST(QNameAST original, NameAST additional, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    NameAST[] origNames = original.names;
    names = new NameAST[origNames.length + 1];
    System.arraycopy(origNames, 0, names, 0, origNames.length);
    names[origNames.length] = additional;
  }

  /**
   * Creates a new QNameAST with the specified list of names.
   * @param names - the names to include in this QNameAST
   * @param parseInfo - the parsing information
   */
  public QNameAST(NameAST[] names, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.names = names;
  }

  /**
   * @return the names
   */
  public NameAST[] getNames()
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
