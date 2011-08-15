package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.QNameAST;

/*
 * Created on 15 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SuperAccessExpressionAST extends ExpressionAST
{

  private QNameAST qualifier;

  /**
   * Creates a new SuperAccessExpressionAST with the specified qualifier.
   * @param qualifier - the qualifier for the super expression, or null if no qualifier is specified
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public SuperAccessExpressionAST(QNameAST qualifier, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.qualifier = qualifier;
  }

  /**
   * @return the qualifier
   */
  public QNameAST getQualifier()
  {
    return qualifier;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (qualifier != null)
    {
      return qualifier + ".super";
    }
    return "super";
  }

}
