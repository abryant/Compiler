package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.Name;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgument
{

  private ParseInfo parseInfo;

  private Name name;
  private PointerType[] superTypes; // the types that this type argument extends
  private PointerType[] subTypes;   // the types that this type argument is a superclass of

  /**
   * Creates a new TypeArgument with the specified name, super type and sub type.
   * @param name - the name of this type argument
   * @param superTypes - the types that this argument must extend
   * @param subTypes - the types that this argument must be a superclass of
   * @param parseInfo - the parsing information
   */
  public TypeArgument(Name name, PointerType[] superTypes, PointerType[] subTypes, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.superTypes = superTypes;
    this.subTypes = subTypes;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the types that this argument must extend
   */
  public PointerType[] getSuperTypes()
  {
    return superTypes;
  }

  /**
   * @return the types that this argument must be a superclass of
   */
  public PointerType[] getSubTypes()
  {
    return subTypes;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(name);
    if (superTypes.length > 0)
    {
      buffer.append(" extends ");
      for (int i = 0; i < superTypes.length; i++)
      {
        buffer.append(superTypes[i]);
        if (i != superTypes.length - 1)
        {
          buffer.append(" & ");
        }
      }
    }
    if (subTypes.length > 0)
    {
      buffer.append(" super ");
      for (int i = 0; i < subTypes.length; i++)
      {
        buffer.append(subTypes[i]);
        if (i != subTypes.length - 1)
        {
          buffer.append(" & ");
        }
      }
    }
    return buffer.toString();
  }

}
