package compiler.language.ast.type;

import compiler.language.LexicalPhrase;

/*
 * Created on 12 Jul 2010
 */

/**
 * The base class for all types of type argument, e.g. NormalTypeArgumentAST and WildcardTypeArgumentAST
 * @author Anthony Bryant
 */
public abstract class TypeArgumentAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new TypeArgumentAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TypeArgumentAST(LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

}
