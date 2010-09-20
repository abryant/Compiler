package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_OR_EXPRESSION;
import static compiler.language.parser.ParseType.COLON;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.QUESTION_MARK;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.InlineIfExpression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InlineIfExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production NO_CHANGE_PRODUCTION      = new Production(BOOLEAN_OR_EXPRESSION);
  private static final Production IF_PRODUCTION             = new Production(BOOLEAN_OR_EXPRESSION,         QUESTION_MARK, EXPRESSION, COLON, INLINE_IF_EXPRESSION);
  private static final Production IF_QNAME_PRODUCTION       = new Production(BOOLEAN_OR_EXPRESSION,         QUESTION_MARK, EXPRESSION, COLON, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production QNAME_IF_PRODUCTION       = new Production(QNAME_OR_LESS_THAN_EXPRESSION, QUESTION_MARK, EXPRESSION, COLON, INLINE_IF_EXPRESSION);
  private static final Production QNAME_IF_QNAME_PRODUCTION = new Production(QNAME_OR_LESS_THAN_EXPRESSION, QUESTION_MARK, EXPRESSION, COLON, QNAME_OR_LESS_THAN_EXPRESSION);

  public InlineIfExpressionRule()
  {
    super(INLINE_IF_EXPRESSION, NO_CHANGE_PRODUCTION,
                                IF_PRODUCTION, IF_QNAME_PRODUCTION, QNAME_IF_PRODUCTION, QNAME_IF_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (NO_CHANGE_PRODUCTION.equals(production))
    {
      // return the existing Expression
      return args[0];
    }
    if (IF_PRODUCTION.equals(production) || IF_QNAME_PRODUCTION.equals(production) || QNAME_IF_PRODUCTION.equals(production) || QNAME_IF_QNAME_PRODUCTION.equals(production))
    {
      Expression firstExpression = (Expression) args[0];
      Expression secondExpression = (Expression) args[2];
      Expression lastExpression = (Expression) args[4];
      return new InlineIfExpression(firstExpression, secondExpression, lastExpression,
                                    ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo(), (ParseInfo) args[3], lastExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
