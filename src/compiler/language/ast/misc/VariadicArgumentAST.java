package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VariadicArgumentAST extends SingleArgumentAST
{

  /**
   * Creates a new Variadic ArgumentAST with the specified type and name
   * @param modifiers - the modifiers of the argument
   * @param type - the type of the argument
   * @param name - the name of the argument
   * @param parseInfo - the parsing information
   */
  public VariadicArgumentAST(ModifierAST[] modifiers, TypeAST type, NameAST name, ParseInfo parseInfo)
  {
    super(modifiers, type, name, parseInfo);
  }

  /**
   * @see compiler.language.ast.misc.SingleArgumentAST#toString()
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
    buffer.append("... ");
    buffer.append(name);
    return buffer.toString();
  }
}
