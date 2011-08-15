package compiler.language.ast.type;

import compiler.language.LexicalPhrase;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterTypeAST extends PrimitiveTypeAST
{

  /**
   * Creates a new CharacterTypeAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public CharacterTypeAST(LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "char";
  }
}
