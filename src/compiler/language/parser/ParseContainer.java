package compiler.language.parser;

import compiler.language.LexicalPhrase;

/*
 * Created on 9 Aug 2010
 */

/**
 * Contains a single AST node which has been parsed, which has some surrounding syntax represented by the container's LexicalPhrase object.
 * This should only ever be used in parsing, never in the final AST, and is used to store intermediary results.
 *
 * For example, a PointerTypeAST has its own LexicalPhrase which relates to the pointer type itself, but the result of the
 * PointerTypeRAngle rule produces something which contains the data of just a PointerTypeAST, and also the combined
 * LexicalPhrase of both the PointerTypeAST and the trailing right angle bracket. This rule therefore returns a
 * ParseContainer&lt;PointerType&gt; so that both of the LexicalPhrase objects can be preserved.
 * @author Anthony Bryant
 * @param <E> - the type of object to store
 */
public class ParseContainer<E>
{

  private E item;
  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new ParseContainer which stores the specified item and LexicalPhrase
   * @param item - the item to store
   * @param lexicalPhrase - the lexical phrase associated with the contained AST node
   */
  public ParseContainer(E item, LexicalPhrase lexicalPhrase)
  {
    this.item = item;
    this.lexicalPhrase = lexicalPhrase;
  }

  /**
   * @return the item
   */
  public E getItem()
  {
    return item;
  }
  /**
   * @return the lexical phrase associated with the contained AST node
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

}
