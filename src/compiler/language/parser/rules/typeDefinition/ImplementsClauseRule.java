package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.IMPLEMENTS_CLAUSE;
import static compiler.language.parser.ParseType.IMPLEMENTS_KEYWORD;
import static compiler.language.parser.ParseType.INTERFACE_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImplementsClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {IMPLEMENTS_KEYWORD, INTERFACE_LIST};

  public ImplementsClauseRule()
  {
    super(IMPLEMENTS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
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
