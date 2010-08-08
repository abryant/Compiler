package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ArrayAccessExpression;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayElementAssignee extends Assignee
{

  private ArrayAccessExpression arrayElement;

  /**
   * Creates a new ArrayElementAssignee which assigns to the specified array element
   * @param arrayElement - the ArrayAccessExpression to assign to
   * @param parseInfo - the parsing information
   */
  public ArrayElementAssignee(ArrayAccessExpression arrayElement, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.arrayElement = arrayElement;
  }

  /**
   * @return the arrayElement
   */
  public ArrayAccessExpression getArrayElement()
  {
    return arrayElement;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.Assignee#toString()
   */
  @Override
  public String toString()
  {
    return arrayElement.toString();
  }

}
