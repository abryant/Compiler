package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;

/*
 * Created on 12 Aug 2010
 */

/**
 * A parse-time only type for storing the number of dimensions in an array instanciation expression.
 *
 * This should NEVER be used as an AST node, as it just stores an integer.
 *
 * @author Anthony Bryant
 */
public class DimensionsAST
{

  private LexicalPhrase lexicalPhrase;

  private int dimensions;

  /**
   * Creates a new DimensionsAST object representing the specified number of dimensions
   * @param dimensions - the number of dimensions to store
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public DimensionsAST(int dimensions, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.dimensions = dimensions;
  }

  /**
   * @return the dimensions
   */
  public int getDimensions()
  {
    return dimensions;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

}
