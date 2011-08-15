package compiler.language.ast.terminal;

import compiler.language.LexicalPhrase;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingLiteralAST
{

  private LexicalPhrase lexicalPhrase;

  private String stringRepresentation;

  /**
   * Creates a new floating literal with the specified string representation.
   * @param stringRepresentation - the string representation of the floating literal
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FloatingLiteralAST(String stringRepresentation, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.stringRepresentation = stringRepresentation;
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
