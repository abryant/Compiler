package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;

/*
 * Created on 15 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SuperAccessExpression extends Expression
{

  private QName qualifier;

  /**
   * Creates a new SuperAccessExpression with the specified qualifier.
   * @param qualifier - the qualifier for the super expression, or null if no qualifier is specified
   * @param parseInfo - the parsing information
   */
  public SuperAccessExpression(QName qualifier, ParseInfo parseInfo)
  {
    super(parseInfo);
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
      return qualifier + ".super";
    }
    return "super";
  }

}