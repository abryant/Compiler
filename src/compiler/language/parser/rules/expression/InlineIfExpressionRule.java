package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_OR_EXPRESSION;
import static compiler.language.parser.ParseType.COLON;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.InlineIfExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class InlineIfExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NO_CHANGE_PRODUCTION      = new Production<ParseType>(BOOLEAN_OR_EXPRESSION);
  private static final Production<ParseType> IF_PRODUCTION             = new Production<ParseType>(BOOLEAN_OR_EXPRESSION,         QUESTION_MARK, EXPRESSION, COLON, INLINE_IF_EXPRESSION);
  private static final Production<ParseType> IF_QNAME_PRODUCTION       = new Production<ParseType>(BOOLEAN_OR_EXPRESSION,         QUESTION_MARK, EXPRESSION, COLON, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_IF_PRODUCTION       = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, QUESTION_MARK, EXPRESSION, COLON, INLINE_IF_EXPRESSION);
  private static final Production<ParseType> QNAME_IF_QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, QUESTION_MARK, EXPRESSION, COLON, QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public InlineIfExpressionRule()
  {
    super(INLINE_IF_EXPRESSION, NO_CHANGE_PRODUCTION,
                                IF_PRODUCTION, IF_QNAME_PRODUCTION, QNAME_IF_PRODUCTION, QNAME_IF_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NO_CHANGE_PRODUCTION.equals(production))
    {
      // return the existing ExpressionAST
      return args[0];
    }
    if (IF_PRODUCTION.equals(production) || IF_QNAME_PRODUCTION.equals(production) || QNAME_IF_PRODUCTION.equals(production) || QNAME_IF_QNAME_PRODUCTION.equals(production))
    {
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      ExpressionAST secondExpression = (ExpressionAST) args[2];
      ExpressionAST lastExpression = (ExpressionAST) args[4];
      return new InlineIfExpressionAST(firstExpression, secondExpression, lastExpression,
                                    ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo(), (ParseInfo) args[3], lastExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
