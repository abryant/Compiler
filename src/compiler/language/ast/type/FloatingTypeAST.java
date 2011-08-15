package compiler.language.ast.type;

import compiler.language.LexicalPhrase;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingTypeAST extends PrimitiveTypeAST
{

  private FloatingTypeLengthAST typeLength;

  /**
   * Creates a new Floating type with the specified length
   * @param typeLength - the length of the type
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FloatingTypeAST(FloatingTypeLengthAST typeLength, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.typeLength = typeLength;
  }

  /**
   * @return the typeLength
   */
  public FloatingTypeLengthAST getTypeLength()
  {
    return typeLength;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return typeLength.toString();
  }
}
