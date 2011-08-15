package compiler.language.conceptual;

import compiler.language.LexicalPhrase;

/*
 * Created on 23 Dec 2010
 */

/**
 * An exception that is thrown if something is conceptually wrong with the program. For example if an access specifier or modifier is invalid.
 * TODO: eventually, everywhere throwing this should be replaced by printing an error message in a standard way
 * @author Anthony Bryant
 */
public class ConceptualException extends Exception
{
  private static final long serialVersionUID = 1L;

  private LexicalPhrase[] lexicalPhrases;

  /**
   * Creates a new ConceptualException with the specified message and LexicalPhrase
   * @param message - the message for this ConceptualException
   * @param lexicalPhrases - all of the lexical phrases specifying where the mistake occurred (this can contain multiple LexicalPhrase objects)
   */
  public ConceptualException(String message, LexicalPhrase... lexicalPhrases)
  {
    super(message);
    this.lexicalPhrases = lexicalPhrases;
  }

  /**
   * Creates a new ConceptualException with the specified message and LexicalPhrase
   * @param message - the message for this ConceptualException
   * @param cause - the cause of this exception
   * @param lexicalPhrases - all of the lexical phrases specifying where the mistake occurred (this can contain multiple LexicalPhrase objects)
   */
  public ConceptualException(String message, Throwable cause, LexicalPhrase... lexicalPhrases)
  {
    super(message, cause);
    this.lexicalPhrases = lexicalPhrases;
  }

  /**
   * @return all of the LexicalPhrase objects related to this exception
   */
  public LexicalPhrase[] getLexicalPhrases()
  {
    return lexicalPhrases;
  }

}
