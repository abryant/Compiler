package compiler.language.ast.expression;

import compiler.language.ast.misc.Parameter;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodCallExpression extends StatementExpression
{

  private QName qualifier = null;
  private boolean superQualifier = false;
  private Expression expressionQualifier = null;

  private Name name;
  private Parameter[] parameters;

  /**
   * Creates a new MethodCallExpression with the specified qualifier, name and parameters
   * @param qualifier - the qualifier of the method call
   * @param superQualifier - true if the qualifier is of the form A.B.super, false otherwise
   * @param name - the name of the method
   * @param parameters - the parameters to the method
   */
  public MethodCallExpression(QName qualifier, boolean superQualifier, Name name, Parameter[] parameters)
  {
    this.qualifier = qualifier;
    this.superQualifier = superQualifier;
    this.name = name;
    this.parameters = parameters;
  }

  /**
   * Creates a new MethodCallExpression with the specified qualifier, name and parameters
   * @param superQualifier - true if the qualifier is super, false if there is no qualifier
   * @param name - the name of the method
   * @param parameters - the parameters to the method
   */
  public MethodCallExpression(boolean superQualifier, Name name, Parameter[] parameters)
  {
    this.superQualifier = superQualifier;
    this.name = name;
    this.parameters = parameters;
  }

  /**
   * Creates a new MethodCallExpression with the specified expression qualifier, name and parameters
   * @param expressionQualifier - the expression qualifier of the method call
   * @param name - the name of the method
   * @param parameters - the parameters to the method
   */
  public MethodCallExpression(Expression expressionQualifier, Name name, Parameter[] parameters)
  {
    this.expressionQualifier = expressionQualifier;
    this.name = name;
    this.parameters = parameters;
  }

  /**
   * @return the qualifier
   */
  public QName getQualifier()
  {
    return qualifier;
  }

  /**
   * @return true if this method call's qualifier includes a super keyword, false otherwise
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
   * @return the parameters
   */
  public Parameter[] getParameters()
  {
    return parameters;
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
    }
    else if (superQualifier)
    {
      buffer.append("super.");
    }
    buffer.append(name);
    buffer.append("(");
    for (int i = 0; i < parameters.length; i++)
    {
      buffer.append(parameters[i]);
      if (i != parameters.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }
}
