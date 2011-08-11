package compiler.language.parser;

import compiler.language.ast.ParseInfo;

/*
 * Created on 21 Aug 2010
 */

/**
 * A class containing various static utility methods which are useful while parsing the language.
 * @author Anthony Bryant
 */
public class ParseUtil
{

  /**
   * Private constructor to make the class uninstantiatable.
   */
  private ParseUtil()
  {
    // do nothing
  }

  /**
   * Splits the specified ParseInfo for a DOUBLE_RANGLE token in half and returns the ParseInfo for the first half of it.
   * @param doubleRAngleInfo - the ParseInfo of the DOUBLE_RANGLE token
   * @return the ParseInfo of the first angle bracket in the DOUBLE_RANGLE
   * @throws LanguageParseException - if the DOUBLE_RANGLE token takes up multiple lines or is not 2 characters long
   */
  public static ParseInfo splitDoubleRAngle(ParseInfo doubleRAngleInfo) throws LanguageParseException
  {
    int line = doubleRAngleInfo.getLine();
    int startColumn = doubleRAngleInfo.getStartColumn();
    if (doubleRAngleInfo.getEndColumn() - startColumn != 2)
    {
      throw new LanguageParseException("Found a DOUBLE_RANGLE \">>\" token which is not 2 characters long", doubleRAngleInfo);
    }
    return new ParseInfo(line, doubleRAngleInfo.getLineText(), startColumn, startColumn + 1);
  }

  /**
   * Splits the specified ParseInfo for a TRIPLE_RANGLE token and returns the ParseInfo for the first angle bracket in it.
   * @param tripleRAngleInfo - the ParseInfo of the TRIPLE_RANGLE token
   * @return the ParseInfo of the first angle bracket in the TRIPLE_RANGLE
   * @throws LanguageParseException - if the TRIPLE_RANGLE token takes up multiple lines or is not 3 characters long
   */
  public static ParseInfo splitTripleRAngleFirst(ParseInfo tripleRAngleInfo) throws LanguageParseException
  {
    int line = tripleRAngleInfo.getLine();
    int startColumn = tripleRAngleInfo.getStartColumn();
    if (tripleRAngleInfo.getEndColumn() - startColumn != 3)
    {
      throw new LanguageParseException("Found a TRIPLE_RANGLE \">>>\" token which is not 3 characters long", tripleRAngleInfo);
    }
    return new ParseInfo(line, tripleRAngleInfo.getLineText(), startColumn, startColumn + 1);
  }

  /**
   * Splits the specified ParseInfo for a TRIPLE_RANGLE token and returns the ParseInfo for the first two angle brackets in it.
   * @param tripleRAngleInfo - the ParseInfo of the TRIPLE_RANGLE token
   * @return the ParseInfo of the first two angle brackets in the TRIPLE_RANGLE
   * @throws LanguageParseException - if the TRIPLE_RANGLE token takes up multiple lines or is not 3 characters long
   */
  public static ParseInfo splitTripleRAngleFirstTwo(ParseInfo tripleRAngleInfo) throws LanguageParseException
  {
    int line = tripleRAngleInfo.getLine();
    int startColumn = tripleRAngleInfo.getStartColumn();
    if (tripleRAngleInfo.getEndColumn() - startColumn != 3)
    {
      throw new LanguageParseException("Found a TRIPLE_RANGLE \">>>\" token which is not 3 characters long", tripleRAngleInfo);
    }
    return new ParseInfo(line, tripleRAngleInfo.getLineText(), startColumn, startColumn + 2);
  }

}
