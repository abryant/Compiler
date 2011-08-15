package compiler.language.ast.terminal;

import java.math.BigInteger;

import compiler.language.LexicalPhrase;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteralAST
{

  private LexicalPhrase lexicalPhrase;

  private BigInteger value;
  private String stringRepresentation;

  /**
   * Creates a new integer literal with the specified string representation.
   * @param value - the value of this integer literal
   * @param stringRepresentation - the string representation of the integer literal
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public IntegerLiteralAST(BigInteger value, String stringRepresentation, LexicalPhrase lexicalPhrase)
  {
    this.value = value;
    this.lexicalPhrase = lexicalPhrase;
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @return the value
   */
  public BigInteger getValue()
  {
    return value;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }
}
