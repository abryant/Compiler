package compiler.language.ast;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Property extends Member
{

  private Type type;
  private Name name;
  private Expression expression;
  private AccessSpecifier assignAccess;
  private Block assignBlock;
  private AccessSpecifier retrieveAccess;
  private Block retrieveBlock;

  /**
   * Creates a new property with the specified type, name, assigner, retriever and access specifiers
   * @param type - the type of variable to store
   * @param name - the name of the property
   * @param expression - the expression for the initial value of the property
   * @param assignAccess - the access specifier of the assigner
   * @param assignBlock - the assigner block for the property
   * @param retrieveAccess - the access specifier of the retriever
   * @param retrieveBlock - the retriever block for the property
   */
  public Property(Type type, Name name, Expression expression, AccessSpecifier assignAccess, Block assignBlock, AccessSpecifier retrieveAccess, Block retrieveBlock)
  {
    this.type = type;
    this.name = name;
    this.expression = expression;
    this.assignAccess = assignAccess;
    this.assignBlock = assignBlock;
    this.retrieveAccess = retrieveAccess;
    this.retrieveBlock = retrieveBlock;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
  {
    return expression;
  }

  /**
   * @return the assigner's access specifier
   */
  public AccessSpecifier getAssignAccess()
  {
    return assignAccess;
  }

  /**
   * @return the assigner's block
   */
  public Block getAssignBlock()
  {
    return assignBlock;
  }

  /**
   * @return the retriever's access specifier
   */
  public AccessSpecifier getRetrieveAccess()
  {
    return retrieveAccess;
  }

  /**
   * @return the retriever's block
   */
  public Block getRetrieveBlock()
  {
    return retrieveBlock;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("property ");
    buffer.append(type);
    buffer.append(" ");
    buffer.append(name);
    if (expression != null)
    {
      buffer.append(" = ");
      buffer.append(expression);
    }
    if (assignAccess != null || assignBlock != null)
    {
      buffer.append(assignBlock == null ? " " : "\n");
      if (assignAccess != null)
      {
        buffer.append(assignAccess);
        buffer.append(" ");
      }
      buffer.append("assign");
      if (assignBlock != null)
      {
        buffer.append("\n");
        buffer.append(assignBlock);
      }
    }
    if (retrieveAccess != null || retrieveBlock != null)
    {
      buffer.append(retrieveBlock == null ? " " : "\n");
      if (retrieveAccess != null)
      {
        buffer.append(retrieveAccess);
        buffer.append(" ");
      }
      buffer.append("retrieve");
      if (retrieveBlock != null)
      {
        buffer.append("\n");
        buffer.append(retrieveBlock);
      }
    }
    buffer.append(";");
    return buffer.toString();
  }
}
