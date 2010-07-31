package compiler.language.ast;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VariadicArgument extends SingleArgument
{

  /**
   * Creates a new Variadic Argument with the specified type and name
   * @param modifiers - the modifiers of the argument
   * @param type - the type of the argument
   * @param name - the name of the argument
   */
  public VariadicArgument(Modifier[] modifiers, Type type, Name name)
  {
    super(modifiers, type, name);
  }

  /**
   * @see compiler.language.ast.SingleArgument#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (Modifier modifier : modifiers)
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
