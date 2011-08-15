package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class NilLiteralExpressionAST extends ExpressionAST
{

  /**
   * Creates a new NilLiteralExpressionAST
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public NilLiteralExpressionAST(LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "nil";
  }
}
