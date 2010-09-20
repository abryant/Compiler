package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.FOR_INIT;
import static compiler.language.parser.ParseType.LOCAL_DECLARATION;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;

import compiler.language.ast.expression.StatementExpression;
import compiler.language.ast.statement.ExpressionStatement;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ForInitRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION       = new Production<ParseType>();
  private static final Production<ParseType> DECLARATION_PRODUCTION = new Production<ParseType>(LOCAL_DECLARATION);
  private static final Production<ParseType> EXPRESSION_PRODUCTION  = new Production<ParseType>(STATEMENT_EXPRESSION);
  private static final Production<ParseType> ASSIGNMENT_PRODUCTION  = new Production<ParseType>(ASSIGNMENT);

  @SuppressWarnings("unchecked")
  public ForInitRule()
  {
    super(FOR_INIT, EMPTY_PRODUCTION, DECLARATION_PRODUCTION, EXPRESSION_PRODUCTION, ASSIGNMENT_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (DECLARATION_PRODUCTION.equals(production) || ASSIGNMENT_PRODUCTION.equals(production))
    {
      // a Statement has already been generated, so return it
      return args[0];
    }
    if (EXPRESSION_PRODUCTION.equals(production))
    {
      StatementExpression expression = (StatementExpression) args[0];
      return new ExpressionStatement(expression, expression.getParseInfo());
    }
    throw badTypeList();
  }

}
