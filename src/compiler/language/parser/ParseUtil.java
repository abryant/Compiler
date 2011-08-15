package compiler.language.parser;

import compiler.language.LexicalPhrase;

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
   * Splits the specified LexicalPhrase for a DOUBLE_RANGLE token in half and returns the LexicalPhrase for the first half of it.
   * @param doubleRAnglePhrase - the LexicalPhrase of the DOUBLE_RANGLE token
   * @return the LexicalPhrase of the first angle bracket in the DOUBLE_RANGLE
   * @throws LanguageParseException - if the DOUBLE_RANGLE token takes up multiple lines or is not 2 characters long
   */
  public static LexicalPhrase splitDoubleRAngle(LexicalPhrase doubleRAnglePhrase) throws LanguageParseException
  {
    int line = doubleRAnglePhrase.getLine();
    int startColumn = doubleRAnglePhrase.getStartColumn();
    if (doubleRAnglePhrase.getEndColumn() - startColumn != 2)
    {
      throw new LanguageParseException("Found a DOUBLE_RANGLE \">>\" token which is not 2 characters long", doubleRAnglePhrase);
    }
    return new LexicalPhrase(line, doubleRAnglePhrase.getLineText(), startColumn, startColumn + 1);
  }

  /**
   * Splits the specified LexicalPhrase for a TRIPLE_RANGLE token and returns the LexicalPhrase for the first angle bracket in it.
   * @param tripleRAnglePhrase - the LexicalPhrase of the TRIPLE_RANGLE token
   * @return the LexicalPhrase of the first angle bracket in the TRIPLE_RANGLE
   * @throws LanguageParseException - if the TRIPLE_RANGLE token takes up multiple lines or is not 3 characters long
   */
  public static LexicalPhrase splitTripleRAngleFirst(LexicalPhrase tripleRAnglePhrase) throws LanguageParseException
  {
    int line = tripleRAnglePhrase.getLine();
    int startColumn = tripleRAnglePhrase.getStartColumn();
    if (tripleRAnglePhrase.getEndColumn() - startColumn != 3)
    {
      throw new LanguageParseException("Found a TRIPLE_RANGLE \">>>\" token which is not 3 characters long", tripleRAnglePhrase);
    }
    return new LexicalPhrase(line, tripleRAnglePhrase.getLineText(), startColumn, startColumn + 1);
  }

  /**
   * Splits the specified LexicalPhrase for a TRIPLE_RANGLE token and returns the LexicalPhrase for the first two angle brackets in it.
   * @param tripleRAnglePhrase - the LexicalPhrase of the TRIPLE_RANGLE token
   * @return the LexicalPhrase of the first two angle brackets in the TRIPLE_RANGLE
   * @throws LanguageParseException - if the TRIPLE_RANGLE token takes up multiple lines or is not 3 characters long
   */
  public static LexicalPhrase splitTripleRAngleFirstTwo(LexicalPhrase tripleRAnglePhrase) throws LanguageParseException
  {
    int line = tripleRAnglePhrase.getLine();
    int startColumn = tripleRAnglePhrase.getStartColumn();
    if (tripleRAnglePhrase.getEndColumn() - startColumn != 3)
    {
      throw new LanguageParseException("Found a TRIPLE_RANGLE \">>>\" token which is not 3 characters long", tripleRAnglePhrase);
    }
    return new LexicalPhrase(line, tripleRAnglePhrase.getLineText(), startColumn, startColumn + 2);
  }

}
