package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.VERSION_NUMBER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.VersionNumber;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VersionNumberRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {INTEGER_LITERAL};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {VERSION_NUMBER, DOT, INTEGER_LITERAL};

  public VersionNumberRule()
  {
    super(VERSION_NUMBER, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      IntegerLiteral literal = (IntegerLiteral) args[0];
      return new VersionNumber(new IntegerLiteral[] {literal}, literal.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      VersionNumber oldNumber = (VersionNumber) args[0];
      IntegerLiteral[] oldList = oldNumber.getVersionParts();
      IntegerLiteral[] newList = new IntegerLiteral[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      IntegerLiteral literal = (IntegerLiteral) args[2];
      newList[oldList.length] = literal;
      return new VersionNumber(newList, ParseInfo.combine(oldNumber.getParseInfo(), (ParseInfo) args[1], literal.getParseInfo()));
    }
    throw badTypeList();
  }

}
