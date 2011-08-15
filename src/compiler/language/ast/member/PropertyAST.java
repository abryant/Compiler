package compiler.language.ast.member;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PropertyAST extends MemberAST
{

  private ModifierAST[] modifiers;
  private TypeAST type;
  private NameAST name;
  private ExpressionAST expression;
  private AccessSpecifierAST setterAccess;
  private BlockAST setterBlock;
  private AccessSpecifierAST getterAccess;
  private BlockAST getterBlock;

  /**
   * Creates a new property with the specified type, name, setter, getter and access specifiers
   * @param modifiers - the modifiers for this property
   * @param type - the type of variable to store
   * @param name - the name of the property
   * @param expression - the expression for the initial value of the property
   * @param setterAccess - the access specifier of the setter
   * @param setterBlock - the setter block for the property
   * @param getterAccess - the access specifier of the getter
   * @param getterBlock - the getter block for the property
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public PropertyAST(ModifierAST[] modifiers, TypeAST type, NameAST name, ExpressionAST expression, AccessSpecifierAST setterAccess, BlockAST setterBlock, AccessSpecifierAST getterAccess, BlockAST getterBlock, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.modifiers = modifiers;
    this.type = type;
    this.name = name;
    this.expression = expression;
    this.setterAccess = setterAccess;
    this.setterBlock = setterBlock;
    this.getterAccess = getterAccess;
    this.getterBlock = getterBlock;
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
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the setter's access specifier
   */
  public AccessSpecifierAST getSetterAccess()
  {
    return setterAccess;
  }

  /**
   * @return the setter's block
   */
  public BlockAST getSetterBlock()
  {
    return setterBlock;
  }

  /**
   * @return the getter's access specifier
   */
  public AccessSpecifierAST getGetterAccess()
  {
    return getterAccess;
  }

  /**
   * @return the getter's block
   */
  public BlockAST getGetterBlock()
  {
    return getterBlock;
  }

  /**
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
    buffer.append("property ");
    buffer.append(type);
    buffer.append(" ");
    buffer.append(name);
    if (expression != null)
    {
      buffer.append(" = ");
      buffer.append(expression);
    }
    if (setterAccess != null || setterBlock != null)
    {
      buffer.append("\n");
      if (setterAccess != null)
      {
        buffer.append(setterAccess);
        buffer.append(" ");
      }
      buffer.append("setter");
      if (setterBlock != null)
      {
        buffer.append("\n");
        buffer.append(setterBlock);
      }
    }
    if (getterAccess != null || getterBlock != null)
    {
      buffer.append("\n");
      if (getterAccess != null)
      {
        buffer.append(getterAccess);
        buffer.append(" ");
      }
      buffer.append("getter");
      if (getterBlock != null)
      {
        buffer.append("\n");
        buffer.append(getterBlock);
      }
    }
    buffer.append(";");
    return buffer.toString();
  }
}
