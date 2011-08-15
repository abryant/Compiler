package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
import compiler.language.ast.member.FieldAST;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 12 Aug 2010
 */

/**
 * An assignee that can be used in a FieldAST Declaration
 * @author Anthony Bryant
 */
public class DeclarationAssigneeAST
{

  private LexicalPhrase lexicalPhrase;

  private NameAST name;

  private FieldAST enclosingField;

  /**
   * Creates a new DeclarationAssigneeAST with the specified name
   * @param name - the name of the assignee, or null if no name was intended for this assignee
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public DeclarationAssigneeAST(NameAST name, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.name = name;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the enclosingField
   */
  public FieldAST getEnclosingField()
  {
    return enclosingField;
  }

  /**
   * @param enclosingField - the enclosingField to set
   */
  public void setEnclosingField(FieldAST enclosingField)
  {
    this.enclosingField = enclosingField;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (name == null)
    {
      return "_";
    }
    return name.toString();
  }
}
