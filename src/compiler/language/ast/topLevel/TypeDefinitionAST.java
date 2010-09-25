package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public abstract class TypeDefinitionAST extends MemberAST
{

  /**
   * Creates a new TypeDefinitionAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public TypeDefinitionAST(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

}
