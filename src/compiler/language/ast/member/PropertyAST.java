package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PropertyAST extends MemberAST
{

  private TypeAST type;
  private NameAST name;
  private ExpressionAST expression;
  private AccessSpecifierAST assignAccess;
  private BlockAST assignBlock;
  private AccessSpecifierAST retrieveAccess;
  private BlockAST retrieveBlock;
  // TODO: add modifiers, in the Rules and Grammar as well as the AST, then regenerate the parse table

  /**
   * Creates a new property with the specified type, name, assigner, retriever and access specifiers
   * @param type - the type of variable to store
   * @param name - the name of the property
   * @param expression - the expression for the initial value of the property
   * @param assignAccess - the access specifier of the assigner
   * @param assignBlock - the assigner block for the property
   * @param retrieveAccess - the access specifier of the retriever
   * @param retrieveBlock - the retriever block for the property
   * @param parseInfo - the parsing information
   */
  public PropertyAST(TypeAST type, NameAST name, ExpressionAST expression, AccessSpecifierAST assignAccess, BlockAST assignBlock, AccessSpecifierAST retrieveAccess, BlockAST retrieveBlock, ParseInfo parseInfo)
  {
    super(parseInfo);
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
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the assigner's access specifier
   */
  public AccessSpecifierAST getAssignAccess()
  {
    return assignAccess;
  }

  /**
   * @return the assigner's block
   */
  public BlockAST getAssignBlock()
  {
    return assignBlock;
  }

  /**
   * @return the retriever's access specifier
   */
  public AccessSpecifierAST getRetrieveAccess()
  {
    return retrieveAccess;
  }

  /**
   * @return the retriever's block
   */
  public BlockAST getRetrieveBlock()
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
