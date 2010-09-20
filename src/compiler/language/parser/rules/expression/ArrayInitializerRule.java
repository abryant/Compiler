package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INITIALIZER;
import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.EXPRESSION_LIST;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayInitializerRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(LBRACE, EXPRESSION_LIST, RBRACE);
  private static final Production TRAILING_COMMA_PRODUCTION = new Production(LBRACE, EXPRESSION_LIST, COMMA, RBRACE);
  private static final Production EMPTY_PRODUCTION = new Production(LBRACE, RBRACE);

  public ArrayInitializerRule()
  {
    super(ARRAY_INITIALIZER, PRODUCTION, TRAILING_COMMA_PRODUCTION, EMPTY_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Expression> list = (ParseList<Expression>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    if (TRAILING_COMMA_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Expression> list = (ParseList<Expression>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2], (ParseInfo) args[3]));
      return list;
    }
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<Expression>(ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
