package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.VERSION_NUMBER;

import compiler.language.ast.misc.VersionNumber;
import compiler.language.ast.terminal.IntegerLiteral;
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
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new VersionNumber(new IntegerLiteral[] {(IntegerLiteral) args[0]});
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      IntegerLiteral[] oldList = ((VersionNumber) args[0]).getVersionParts();
      IntegerLiteral[] newList = new IntegerLiteral[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (IntegerLiteral) args[2];
      return new VersionNumber(newList);
    }
    throw badTypeList();
  }

}
