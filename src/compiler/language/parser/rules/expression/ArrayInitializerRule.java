package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INITIALIZER;
import static compiler.language.parser.ParseType.EXPRESSION_LIST;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayInitializerRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LBRACE, EXPRESSION_LIST, RBRACE};
  private static final Object[] EMPTY_PRODUCTION = new Object[] {LBRACE, RBRACE};

  public ArrayInitializerRule()
  {
    super(ARRAY_INITIALIZER, PRODUCTION, EMPTY_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Expression> list = (ParseList<Expression>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    if (types == EMPTY_PRODUCTION)
    {
      return new ParseList<Expression>(ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
