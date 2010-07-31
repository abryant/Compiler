package compiler.language.ast.member;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.type.Type;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Field extends Member
{
  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;
  private Type type;
  private Assignee[] assignees;
  private Expression expression = null;

  /**
   * Creates a new Field with the specified arguments.
   * @param accessSpecifier - the access specifier for this field
   * @param modifiers - the modifiers for this field
   * @param type - the type of this field
   * @param assignees - the assignees for this field
   */
  public Field(AccessSpecifier accessSpecifier, Modifier[] modifiers, Type type, Assignee[] assignees)
  {
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.type = type;
    this.assignees = assignees;
  }

  /**
   * Creates a new Field with the specified arguments.
   * @param accessSpecifier - the access specifier for this field
   * @param modifiers - the modifiers for this field
   * @param type - the type of this field
   * @param assignees - the assignees for this field
   * @param expression - the expression to assign to this field by default
   */
  public Field(AccessSpecifier accessSpecifier, Modifier[] modifiers, Type type, Assignee[] assignees, Expression expression)
  {
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.type = type;
    this.assignees = assignees;
    this.expression = expression;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * @return the modifiers
   */
  public Modifier[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
  }

  /**
   * @return the assignees
   */
  public Assignee[] getAssignees()
  {
    return assignees;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
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
    for (Modifier modifier : modifiers)
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
