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
public class VariadicParameterAST extends SingleParameterAST
{

  /**
   * Creates a new VariadicParameterAST with the specified type and name
   * @param modifiers - the modifiers of the parameter
   * @param type - the type of the parameter
   * @param name - the name of the parameter
   * @param parseInfo - the parsing information
   */
  public VariadicParameterAST(ModifierAST[] modifiers, TypeAST type, NameAST name, ParseInfo parseInfo)
  {
    super(modifiers, type, name, parseInfo);
  }

  /**
   * @see compiler.language.ast.misc.SingleParameterAST#toString()
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
