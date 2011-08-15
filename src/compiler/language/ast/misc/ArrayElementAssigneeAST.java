package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ArrayAccessExpressionAST;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayElementAssigneeAST extends AssigneeAST
{

  private ArrayAccessExpressionAST arrayElement;

  /**
   * Creates a new ArrayElementAssigneeAST which assigns to the specified array element
   * @param arrayElement - the ArrayAccessExpressionAST to assign to
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ArrayElementAssigneeAST(ArrayAccessExpressionAST arrayElement, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.arrayElement = arrayElement;
  }

  /**
   * @return the arrayElement
   */
  public ArrayAccessExpressionAST getArrayElement()
  {
    return arrayElement;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.AssigneeAST#toString()
   */
  @Override
  public String toString()
  {
    return arrayElement.toString();
  }

}
