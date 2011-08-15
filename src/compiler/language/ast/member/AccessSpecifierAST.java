package compiler.language.ast.member;

import compiler.language.LexicalPhrase;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AccessSpecifierAST
{

  private LexicalPhrase lexicalPhrase;

  private AccessSpecifierTypeAST type;

  /**
   * Creates a new access specifier with the specified type.
   * @param type - the type of this access specifier
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public AccessSpecifierAST(AccessSpecifierTypeAST type, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.type = type;
  }

  /**
   * @return the type
   */
  public AccessSpecifierTypeAST getType()
  {
    return type;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type.toString();
  }

}
