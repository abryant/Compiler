package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class LocalDeclarationStatementAST extends StatementAST
{

  private ModifierAST[] modifiers;
  private TypeAST type;
  private DeclarationAssigneeAST[] assignees;
  private ExpressionAST expression;

  /**
   * Creates a new LocalDeclarationStatementAST.
   * @param modifiers - the modifiers for the local variable
   * @param type - the type of the local variables
   * @param assignees - the assignees which are being declared (or defined to the expression)
   * @param expression - the expression to assign to the assignees, or null if this is just a declaration and not also a definition
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public LocalDeclarationStatementAST(ModifierAST[] modifiers, TypeAST type, DeclarationAssigneeAST[] assignees, ExpressionAST expression, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.modifiers = modifiers;
    this.type = type;
    this.assignees = assignees;
    this.expression = expression;
  }

  /**
   * @return the modifiers
   */
  public ModifierAST[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the type
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @return the assignees
   */
  public DeclarationAssigneeAST[] getAssignees()
  {
    return assignees;
  }

  /**
   * @return the expression, or null if no expression is specified
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (ModifierAST modifier : modifiers)
    {
      buffer.append(modifier);
      buffer.append(" ");
    }
    buffer.append(type);
    buffer.append(" ");
    for (int i = 0; i < assignees.length; i++)
    {
      buffer.append(assignees[i]);
      if (i != assignees.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (expression != null)
    {
      buffer.append(" = ");
      buffer.append(expression);
    }
    buffer.append(";");
    return buffer.toString();
  }
}
