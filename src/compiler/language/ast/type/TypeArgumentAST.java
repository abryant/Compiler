package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentAST
{

  private ParseInfo parseInfo;

  private NameAST name;
  private PointerTypeAST[] superTypes; // the types that this type argument extends
  private PointerTypeAST[] subTypes;   // the types that this type argument is a superclass of

  /**
   * Creates a new TypeArgumentAST with the specified name, super type and sub type.
   * @param name - the name of this type argument
   * @param superTypes - the types that this argument must extend
   * @param subTypes - the types that this argument must be a superclass of
   * @param parseInfo - the parsing information
   */
  public TypeArgumentAST(NameAST name, PointerTypeAST[] superTypes, PointerTypeAST[] subTypes, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.superTypes = superTypes;
    this.subTypes = subTypes;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the types that this argument must extend
   */
  public PointerTypeAST[] getSuperTypes()
  {
    return superTypes;
  }

  /**
   * @return the types that this argument must be a superclass of
   */
  public PointerTypeAST[] getSubTypes()
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
