package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.FieldAccessExpression;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAssignee extends Assignee
{

  private FieldAccessExpression field;

  /**
   * Creates a new FieldAssignee which assigns to the specified field
   * @param field - the FieldAccessExpression to assign to
   * @param parseInfo - the parsing information
   */
  public FieldAssignee(FieldAccessExpression field, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.field = field;
  }

  /**
   * @return the field
   */
  public FieldAccessExpression getField()
  {
    return field;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.Assignee#toString()
   */
  @Override
  public String toString()
  {
    return field.toString();
  }

}
