package compiler.language.ast.type;

import compiler.language.LexicalPhrase;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanTypeAST extends PrimitiveTypeAST
{

  /**
   * Creates a new BooleanTypeAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public BooleanTypeAST(LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "boolean";
  }
}
