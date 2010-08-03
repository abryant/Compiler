package compiler.language.ast.expression;

import compiler.language.ast.misc.QName;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThisAccessExpression extends Expression
{

  private QName qualifier;

  /**
   * Creates a new ThisAccessExpression with the specified qualifier.
   * @param qualifier - the qualifier for the this expression, or null if no qualifier is specified
   */
  public ThisAccessExpression(QName qualifier)
  {
    this.qualifier = qualifier;
  }

  /**
   * @return the qualifier
   */
  public QName getQualifier()
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
      return qualifier + ".this";
    }
    return "this";
  }

}
