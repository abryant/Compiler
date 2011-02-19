package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class SingleArgumentAST extends ArgumentAST
{

  protected ModifierAST[] modifiers;
  protected TypeAST type;
  protected NameAST name;

  /**
   * Creates a new single argument with the specified modifiers, type and name.
   * @param modifiers - the modifiers of this argument
   * @param type - the type of this argument
   * @param name - the name of this argument
   * @param parseInfo - the parsing information
   */
  public SingleArgumentAST(ModifierAST[] modifiers, TypeAST type, NameAST name, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.modifiers = modifiers;
    this.type = type;
    this.name = name;
  }

  /**
   * @return the modifiers
   */
  public ModifierAST[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the type of this argument
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @return the name of this argument
   */
  public NameAST getName()
  {
    return name;
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
    buffer.append(type);
    buffer.append(" ");
    buffer.append(name);
    return buffer.toString();
  }
}
