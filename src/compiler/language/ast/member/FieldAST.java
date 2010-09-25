package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAST extends MemberAST
{
  private AccessSpecifierAST accessSpecifier;
  private ModifierAST[] modifiers;
  private TypeAST type;
  private DeclarationAssigneeAST[] assignees;
  private ExpressionAST expression = null;

  /**
   * Creates a new FieldAST with the specified arguments.
   * @param accessSpecifier - the access specifier for this field
   * @param modifiers - the modifiers for this field
   * @param type - the type of this field
   * @param assignees - the assignees for this field
   * @param parseInfo - the parsing information
   */
  public FieldAST(AccessSpecifierAST accessSpecifier, ModifierAST[] modifiers, TypeAST type, DeclarationAssigneeAST[] assignees, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.type = type;
    this.assignees = assignees;
  }

  /**
   * Creates a new FieldAST with the specified arguments.
   * @param accessSpecifier - the access specifier for this field
   * @param modifiers - the modifiers for this field
   * @param type - the type of this field
   * @param assignees - the assignees for this field
   * @param expression - the expression to assign to this field by default
   * @param parseInfo - the parsing information
   */
  public FieldAST(AccessSpecifierAST accessSpecifier, ModifierAST[] modifiers, TypeAST type, DeclarationAssigneeAST[] assignees, ExpressionAST expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.type = type;
    this.assignees = assignees;
    this.expression = expression;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifierAST getAccessSpecifier()
  {
    return accessSpecifier;
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
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (accessSpecifier != null)
    {
      buffer.append(accessSpecifier);
      buffer.append(" ");
    }
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
