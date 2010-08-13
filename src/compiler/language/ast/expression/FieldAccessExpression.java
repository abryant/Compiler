package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAccessExpression extends Expression
{

  private QName qualifier = null;
  private boolean superQualifier = false;
  private Expression expressionQualifier = null;

  private Name name;

  /**
   * Creates a new FieldAccessExpression with the specified qualifier and name
   * @param qualifier - the qualifier for the field
   * @param superQualifier - true if the qualifier is of the form A.B.super, false otherwise
   * @param name - the name of the field
   * @param parseInfo - the parsing information
   */
  public FieldAccessExpression(QName qualifier, boolean superQualifier, Name name, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.qualifier = qualifier;
    this.superQualifier = superQualifier;
    this.name = name;
  }

  /**
   * Creates a new FieldAccessExpression which consists of only the specified qualified name.
   * This is accomplished by splitting the QName up into the list of qualifying names and the final name, and combining the qualifier names back into a QName
   * @param qname - the qualified name, the last element of this will be the name of the variable to access, and the rest will be the qualifier
   * @param parseInfo - the parsing information
   */
  public FieldAccessExpression(QName qname, ParseInfo parseInfo)
  {
    super(parseInfo);
    Name[] names = qname.getNames();
    if (names.length > 1)
    {
      Name[] qualifierNames = new Name[names.length - 1];
      System.arraycopy(names, 0, qualifierNames, 0, qualifierNames.length);

      // build the qualifier's ParseInfo by combining all of its constituent names
      ParseInfo[] qualifierInfo = new ParseInfo[qualifierNames.length];
      for (int i = 0; i < qualifierNames.length; i++)
      {
        qualifierInfo[i] = qualifierNames[i].getParseInfo();
      }

      qualifier = new QName(qualifierNames, ParseInfo.combine(qualifierInfo));
    }
    superQualifier = false;
    name = names[names.length - 1];
  }

  /**
   * Creates a new FieldAccessExpression with the specified qualifier and name
   * @param superQualifier - true if the qualifier is super, false if there is no qualifier
   * @param name - the name of the field to access
   * @param parseInfo - the parsing information
   */
  public FieldAccessExpression(boolean superQualifier, Name name, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.superQualifier = superQualifier;
    this.name = name;
  }

  /**
   * Creates a new FieldAccessExpression with the specified expressionQualifier and name
   * @param expressionQualifier - the expression qualifier for the field
   * @param name - the name of the field
   * @param parseInfo - the parsing information
   */
  public FieldAccessExpression(Expression expressionQualifier, Name name, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expressionQualifier = expressionQualifier;
    this.name = name;
  }

  /**
   * @return the qualifier
   */
  public QName getQualifier()
  {
    return qualifier;
  }

  /**
   * @return true if this field access' qualifier includes a super keyword, false otherwise
   */
  public boolean hasSuperQualifier()
  {
    return superQualifier;
  }

  /**
   * @return the expressionQualifier
   */
  public Expression getExpressionQualifier()
  {
    return expressionQualifier;
  }

  /**
   * @return the name
   */
  public Name getName()
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
      if (superQualifier)
      {
        buffer.append(".super");
      }
      buffer.append(".");
    }
    else if (expressionQualifier != null)
    {
      buffer.append(expressionQualifier);
      buffer.append(".");
    }
    else if (superQualifier)
    {
      buffer.append("super.");
    }
    buffer.append(name);
    return buffer.toString();
  }

}
