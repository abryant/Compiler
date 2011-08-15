package compiler.language.ast.topLevel;

import compiler.language.LexicalPhrase;
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
   * Creates a new TypeDefinitionAST with the specified LexicalPhrase
   * @param name - the name of the type
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TypeDefinitionAST(NameAST name, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
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
