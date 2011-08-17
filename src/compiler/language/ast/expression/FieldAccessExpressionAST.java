package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.QName;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAccessExpressionAST extends ExpressionAST
{

  private QName qualifier = null;
  private ExpressionAST expressionQualifier = null;

  private String name;

  /**
   * Creates a new FieldAccessExpressionAST which consists of only the specified qualified name.
   * This is accomplished by splitting the QName up into the list of qualifying names and the final name, and combining the qualifier names back into a QName
   * @param qname - the qualified name, the last element of this will be the name of the variable to access, and the rest will be the qualifier
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FieldAccessExpressionAST(QName qname, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    String[] names = qname.getNames();
    if (names.length > 1)
    {
      LexicalPhrase[] lexicalPhrases = qname.getLexicalPhrases();
      String[] qualifierNames = new String[names.length - 1];
      LexicalPhrase[] qualifierLexicalPhrases = new LexicalPhrase[qualifierNames.length];
      System.arraycopy(names, 0, qualifierNames, 0, qualifierNames.length);
      System.arraycopy(lexicalPhrases, 0, qualifierLexicalPhrases, 0, qualifierLexicalPhrases.length);

      qualifier = new QName(qualifierNames, qualifierLexicalPhrases);
    }
    name = names[names.length - 1];
  }

  /**
   * Creates a new FieldAccessExpressionAST with the specified expressionQualifier and name
   * @param expressionQualifier - the expression qualifier for the field
   * @param name - the name of the field
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FieldAccessExpressionAST(ExpressionAST expressionQualifier, String name, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
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
   * @return the expressionQualifier
   */
  public ExpressionAST getExpressionQualifier()
  {
    return expressionQualifier;
  }

  /**
   * @return the name
   */
  public String getName()
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
