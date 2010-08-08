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
  private PointerType superType; // the type that this type argument extends
  private PointerType subType;   // the type that this type argument is a superclass of

  /**
   * Creates a new TypeArgument with the specified name, super type and sub type.
   * @param name - the name of this type argument
   * @param superType - the type that this argument must extend
   * @param subType - the type that this argument must be a superclass of
   * @param parseInfo - the parsing information
   */
  public TypeArgument(Name name, PointerType superType, PointerType subType, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.superType = superType;
    this.subType = subType;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the type that this argument must extend
   */
  public PointerType getSuperType()
  {
    return superType;
  }

  /**
   * @return the type that this argument must be a superclass of
   */
  public PointerType getSubType()
  {
    return subType;
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
    if (superType != null)
    {
      buffer.append(" extends ");
      buffer.append(superType);
    }
    if (subType != null)
    {
      buffer.append(" super ");
      buffer.append(subType);
    }
    return buffer.toString();
  }

}
