package compiler.language.conceptual.type;

/*
 * Created on 17 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public abstract class Type
{
  /**
   * Checks whether the specified Type can be assigned to a variable of this Type.
   * @param type - the type to check
   * @return true if the specified other type can be assigned to this one, false otherwise
   */
  public abstract boolean canAssign(Type type);
}
