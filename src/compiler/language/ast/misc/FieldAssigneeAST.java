package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
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
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FieldAssigneeAST(FieldAccessExpressionAST field, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
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
