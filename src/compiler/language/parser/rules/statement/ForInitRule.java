package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.FOR_INIT;
import static compiler.language.parser.ParseType.LOCAL_DECLARATION;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;

import compiler.language.ast.expression.StatementExpression;
import compiler.language.ast.statement.ExpressionStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForInitRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION       = new Object[] {};
  private static final Object[] DECLARATION_PRODUCTION = new Object[] {LOCAL_DECLARATION};
  private static final Object[] EXPRESSION_PRODUCTION  = new Object[] {STATEMENT_EXPRESSION};
  private static final Object[] ASSIGNMENT_PRODUCTION  = new Object[] {ASSIGNMENT};

  public ForInitRule()
  {
    super(FOR_INIT, EMPTY_PRODUCTION, DECLARATION_PRODUCTION, EXPRESSION_PRODUCTION, ASSIGNMENT_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return null;
    }
    if (types == DECLARATION_PRODUCTION || types == ASSIGNMENT_PRODUCTION)
    {
      // a Statement has already been generated, so return it
      return args[0];
    }
    if (types == EXPRESSION_PRODUCTION)
    {
      StatementExpression expression = (StatementExpression) args[0];
      return new ExpressionStatement(expression, expression.getParseInfo());
    }
    throw badTypeList();
  }

}
