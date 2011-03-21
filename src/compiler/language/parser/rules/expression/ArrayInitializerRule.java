package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INITIALIZER;
import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.EXPRESSION_LIST;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArrayInitializerRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LBRACE, EXPRESSION_LIST, RBRACE);
  private static final Production<ParseType> TRAILING_COMMA_PRODUCTION = new Production<ParseType>(LBRACE, EXPRESSION_LIST, COMMA, RBRACE);
  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>(LBRACE, RBRACE);

  @SuppressWarnings("unchecked")
  public ArrayInitializerRule()
  {
    super(ARRAY_INITIALIZER, PRODUCTION, TRAILING_COMMA_PRODUCTION, EMPTY_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> list = (ParseList<ExpressionAST>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
      return list;
    }
    if (TRAILING_COMMA_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> list = (ParseList<ExpressionAST>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2], (ParseInfo) args[3]));
      return list;
    }
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<ExpressionAST>(ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
