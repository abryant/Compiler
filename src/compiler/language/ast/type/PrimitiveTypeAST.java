package compiler.language.ast.type;

import compiler.language.LexicalPhrase;


/*
 * Created on 11 Jul 2010
 */

/**
 * Base class for all primitive types (int, double, char, boolean, etc.)
 * @author Anthony Bryant
 */
public class PrimitiveTypeAST extends TypeAST
{

  /**
   * Creates a new PrimitiveTypeAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public PrimitiveTypeAST(LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
  }

}
