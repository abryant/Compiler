package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.Member;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public abstract class TypeDefinition extends Member
{

  /**
   * Creates a new TypeDefinition with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public TypeDefinition(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

}
