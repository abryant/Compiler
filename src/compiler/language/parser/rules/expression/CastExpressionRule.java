package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.CAST_EXPRESSION;
import static compiler.language.parser.ParseType.CAST_KEYWORD;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import static compiler.language.parser.ParseType.UNARY_EXPRESSION;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.CastExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class CastExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(CAST_KEYWORD, LANGLE, TYPE_RANGLE, UNARY_EXPRESSION);
  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(CAST_KEYWORD, LANGLE, TYPE_RANGLE, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public CastExpressionRule()
  {
    super(CAST_EXPRESSION, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || QNAME_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeAST> type = (ParseContainer<TypeAST>) args[2];
      ExpressionAST expression = (ExpressionAST) args[3];
      return new CastExpressionAST(type.getItem(), expression,
                                ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], type.getParseInfo(), expression.getParseInfo()));
    }
    throw badTypeList();
  }

}
