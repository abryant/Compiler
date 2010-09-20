package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXCLAIMATION_MARK;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.TUPLE_INDEX_EXPRESSION;
import static compiler.language.parser.ParseType.UNARY_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.TupleIndexExpression;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TupleIndexExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NO_CHANGE_PRODUCTION = new Production<ParseType>(UNARY_EXPRESSION);
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TUPLE_INDEX_EXPRESSION, EXCLAIMATION_MARK, INTEGER_LITERAL);
  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION, EXCLAIMATION_MARK, INTEGER_LITERAL);

  @SuppressWarnings("unchecked")
  public TupleIndexExpressionRule()
  {
    super(TUPLE_INDEX_EXPRESSION, NO_CHANGE_PRODUCTION, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NO_CHANGE_PRODUCTION.equals(production))
    {
      // return the existing expression
      return args[0];
    }
    if (PRODUCTION.equals(production) || QNAME_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[0];
      IntegerLiteral index = (IntegerLiteral) args[2];
      return new TupleIndexExpression(expression, index, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1], index.getParseInfo()));
    }
    throw badTypeList();
  }

}
