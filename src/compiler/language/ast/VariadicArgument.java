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
   * @param type - the type of the argument
   * @param name - the name of the argumentk
   */
  public VariadicArgument(Type type, Name name)
  {
    super(type, name);
  }

  /**
   * @see compiler.language.ast.SingleArgument#toString()
   */
  @Override
  public String toString()
  {
    return type + "... " + name;
  }
}
