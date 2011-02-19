package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.FieldAccessExpressionAST;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAssigneeAST extends AssigneeAST
{

  private FieldAccessExpressionAST field;

  /**
   * Creates a new FieldAssigneeAST which assigns to the specified field
   * @param field - the FieldAccessExpressionAST to assign to
   * @param parseInfo - the parsing information
   */
  public FieldAssigneeAST(FieldAccessExpressionAST field, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.field = field;
  }

  /**
   * @return the field
   */
  public FieldAccessExpressionAST getField()
  {
    return field;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.AssigneeAST#toString()
   */
  @Override
  public String toString()
  {
    return field.toString();
  }

}
