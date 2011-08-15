package compiler.language.conceptual;

import compiler.language.LexicalPhrase;

/*
 * Created on 16 Feb 2011
 */

/**
 * An exception that is thrown whenever a name conflict is detected.
 * This happens when two fields (or packages, classes etc) are found to have the same name.
 * @author Anthony Bryant
 */
public class NameConflictException extends Exception
{

  private static final long serialVersionUID = 1L;

  private LexicalPhrase[] lexicalPhrases;

  /**
   * Creates a new blank NameConflictException.
   * @param lexicalPhrases - the LexicalPhrases associated with the name conflict
   */
  public NameConflictException(LexicalPhrase... lexicalPhrases)
  {
    this.lexicalPhrases = lexicalPhrases;
  }

  /**
   * Creates a new NameConflictException with the specified message and array of LexicalPhrase objects.
   * @param message - the message for this exception
   * @param lexicalPhrases - the LexicalPhrases associated with the name conflict
   */
  public NameConflictException(String message, LexicalPhrase... lexicalPhrases)
  {
    super(message);
    this.lexicalPhrases = lexicalPhrases;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase[] getLexicalPhrases()
  {
    return lexicalPhrases;
  }

}
