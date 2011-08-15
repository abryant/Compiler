package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class SingleParameterAST extends ParameterAST
{

  protected ModifierAST[] modifiers;
  protected TypeAST type;
  protected NameAST name;

  /**
   * Creates a new single parameter with the specified modifiers, type and name.
   * @param modifiers - the modifiers of this parameter
   * @param type - the type of this parameter
   * @param name - the name of this parameter
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public SingleParameterAST(ModifierAST[] modifiers, TypeAST type, NameAST name, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
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
   * @return the type of this parameter
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @return the name of this parameter
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
