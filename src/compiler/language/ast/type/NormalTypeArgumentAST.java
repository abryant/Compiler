package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;


/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalTypeArgumentAST extends TypeArgumentAST
{

  private TypeAST type;

  /**
   * Creates a new normal type argument with the specified type.
   * @param type - the type assigned to this type argument
   * @param parseInfo - the parsing information
   */
  public NormalTypeArgumentAST(TypeAST type, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
  }

  /**
   * @return the type
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type.toString();
  }

}
