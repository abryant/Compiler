package compiler.language.ast;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Assignee
{

  private QName name;

  /**
   * Creates a new Assignee that does not assign to anything. This is represented by and underscore in the language syntax.
   */
  public Assignee()
  {
    name = null;
  }

  /**
   * Creates a new Assignee that assigns to the variable with the specified name.
   * @param name - the name of the variable to assign to
   */
  public Assignee(QName name)
  {
    this.name = name;
  }

  /**
   * @return the name of the variable that this Assignee represents
   */
  public QName getName()
  {
    return name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (name == null)
    {
      return "_";
    }
    return name.toString();
  }
}
