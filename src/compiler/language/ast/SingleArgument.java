package compiler.language.ast;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class SingleArgument extends Argument
{

  protected Type type;
  protected Name name;

  /**
   * Creates a new single argument with the specified type and name.
   * @param type - the type of this argument
   * @param name - the name of this argument
   */
  public SingleArgument(Type type, Name name)
  {
    this.type = type;
    this.name = name;
  }

  /**
   * @return the type of this argument
   */
  public Type getType()
  {
    return type;
  }

  /**
   * @return the name of this argument
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type + " " + name;
  }
}
