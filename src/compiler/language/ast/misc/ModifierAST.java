package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierAST
{

  private LexicalPhrase lexicalPhrase;

  private ModifierTypeAST type;

  /**
   * Creates a new modifier of the specified type
   * @param type - the type of this modifier
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ModifierAST(ModifierTypeAST type, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.type = type;
  }

  /**
   * @return the type
   */
  public ModifierTypeAST getType()
  {
    return type;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type.toString();
  }

}
