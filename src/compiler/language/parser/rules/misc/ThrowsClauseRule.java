package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.THROWS_KEYWORD;
import static compiler.language.parser.ParseType.THROWS_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.Rule;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowsClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {THROWS_KEYWORD, THROWS_LIST};

  public ThrowsClauseRule()
  {
    super(THROWS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ParseList<PointerType>(null);
    }
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> list = (ParseList<PointerType>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
