package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;


/*
 * Created on 11 Jul 2010
 */

/**
 * Base class for all primitive types (int, double, char, boolean, etc.)
 * @author Anthony Bryant
 */
public class PrimitiveType extends Type
{

  /**
   * Creates a new PrimitiveType with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public PrimitiveType(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

}
