package compiler.language.parser.rules.expression;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.InstanceOfExpression;
import compiler.language.ast.expression.RelationalExpression;
import compiler.language.ast.expression.RelationalExpressionType;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class RelationalExpressionRule extends Rule
{

  private static final Object[] NO_CHANGE_PRODUCTION = new Object[] {ParseType.BITWISE_OR_EXPRESSION};
  private static final Object[] LESS_THAN_PRODUCTION = new Object[] {ParseType.BITWISE_OR_EXPRESSION, ParseType.LANGLE, ParseType.BITWISE_OR_EXPRESSION};
  private static final Object[] GREATER_THAN_PRODUCTION = new Object[] {ParseType.BITWISE_OR_EXPRESSION, ParseType.RANGLE, ParseType.BITWISE_OR_EXPRESSION};
  private static final Object[] LESS_THAN_EQUAL_PRODUCTION = new Object[] {ParseType.BITWISE_OR_EXPRESSION, ParseType.LANGLE_EQUALS, ParseType.BITWISE_OR_EXPRESSION};
  private static final Object[] GREATER_THAN_EQUAL_PRODUCTION = new Object[] {ParseType.BITWISE_OR_EXPRESSION, ParseType.RANGLE_EQUALS, ParseType.BITWISE_OR_EXPRESSION};
  private static final Object[] INSTANCE_OF_PRODUCTION = new Object[] {ParseType.BITWISE_OR_EXPRESSION, ParseType.INSTANCEOF_KEYWORD, ParseType.TYPE};

  public RelationalExpressionRule()
  {
    super(ParseType.RELATIONAL_EXPRESSION, NO_CHANGE_PRODUCTION, LESS_THAN_PRODUCTION, GREATER_THAN_PRODUCTION,
                                           LESS_THAN_EQUAL_PRODUCTION, GREATER_THAN_EQUAL_PRODUCTION, INSTANCE_OF_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NO_CHANGE_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == INSTANCE_OF_PRODUCTION)
    {
      return new InstanceOfExpression((Expression) args[0], (Type) args[2]);
    }

    // the only remaining productions are RelationalExpression productions
    RelationalExpressionType separator = null;
    if (types == LESS_THAN_PRODUCTION)
    {
      separator = RelationalExpressionType.LESS_THAN;
    }
    else if (types == GREATER_THAN_PRODUCTION)
    {
      separator = RelationalExpressionType.GREATER_THAN;
    }
    else if (types == LESS_THAN_EQUAL_PRODUCTION)
    {
      separator = RelationalExpressionType.LESS_THAN_EQUAL;
    }
    else if (types == GREATER_THAN_EQUAL_PRODUCTION)
    {
      separator = RelationalExpressionType.GREATER_THAN_EQUAL;
    }
    else
    {
      throw badTypeList();
    }

    return new RelationalExpression((Expression) args[0], separator, (Expression) args[2]);
  }

}
