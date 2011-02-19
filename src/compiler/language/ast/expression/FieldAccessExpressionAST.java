package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAccessExpressionAST extends ExpressionAST
{

  private QNameAST qualifier = null;
  private ExpressionAST expressionQualifier = null;

  private NameAST name;

  /**
   * Creates a new FieldAccessExpressionAST which consists of only the specified qualified name.
   * This is accomplished by splitting the QNameAST up into the list of qualifying names and the final name, and combining the qualifier names back into a QNameAST
   * @param qname - the qualified name, the last element of this will be the name of the variable to access, and the rest will be the qualifier
   * @param parseInfo - the parsing information
   */
  public FieldAccessExpressionAST(QNameAST qname, ParseInfo parseInfo)
  {
    super(parseInfo);
    NameAST[] names = qname.getNames();
    if (names.length > 1)
    {
      NameAST[] qualifierNames = new NameAST[names.length - 1];
      System.arraycopy(names, 0, qualifierNames, 0, qualifierNames.length);

      // build the qualifier's ParseInfo by combining all of its constituent names
      ParseInfo[] qualifierInfo = new ParseInfo[qualifierNames.length];
      for (int i = 0; i < qualifierNames.length; i++)
      {
        qualifierInfo[i] = qualifierNames[i].getParseInfo();
      }

      qualifier = new QNameAST(qualifierNames, ParseInfo.combine(qualifierInfo));
    }
    name = names[names.length - 1];
  }

  /**
   * Creates a new FieldAccessExpressionAST with the specified expressionQualifier and name
   * @param expressionQualifier - the expression qualifier for the field
   * @param name - the name of the field
   * @param parseInfo - the parsing information
   */
  public FieldAccessExpressionAST(ExpressionAST expressionQualifier, NameAST name, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expressionQualifier = expressionQualifier;
    this.name = name;
  }

  /**
   * @return the qualifier
   */
  public QNameAST getQualifier()
  {
    return qualifier;
  }

  /**
   * @return the expressionQualifier
   */
  public ExpressionAST getExpressionQualifier()
  {
    return expressionQualifier;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (qualifier != null)
    {
      buffer.append(qualifier);
      buffer.append(".");
    }
    else if (expressionQualifier != null)
    {
      buffer.append(expressionQualifier);
      buffer.append(".");
    }
    buffer.append(name);
    return buffer.toString();
  }

}
