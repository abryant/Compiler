package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public abstract class TypeDefinitionAST extends MemberAST
{

  private NameAST name;

  /**
   * Creates a new TypeDefinitionAST with the specified ParseInfo
   * @param name - the name of the type
   * @param parseInfo - the parsing information
   */
  public TypeDefinitionAST(NameAST name, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.name = name;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

}
