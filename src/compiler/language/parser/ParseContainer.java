package compiler.language.parser;

import compiler.language.ast.ParseInfo;

/*
 * Created on 9 Aug 2010
 */

/**
 * Contains a single AST node which has been parsed, which has some surrounding syntax represented by the container's ParseInfo object.
 * This should only ever be used in parsing, never in the final AST, and is used to store intermediary results.
 *
 * For example, a PointerTypeAST has its own ParseInfo which relates to the pointer type itself, but the result of the
 * PointerTypeRAngle rule produces something which contains the data of just a PointerTypeAST, and also the combined
 * ParseInfo of both the PointerTypeAST and the trailing right angle bracket. This rule therefore returns a
 * ParseContainer&lt;PointerType&gt; so that both of the ParseInfo objects can be preserved.
 * @author Anthony Bryant
 * @param <E> - the type of object to store
 */
public class ParseContainer<E>
{

  private E item;
  private ParseInfo parseInfo;

  /**
   * Creates a new ParseContainer which stores the specified item and ParseInfo
   * @param item - the item to store
   * @param parseInfo - the parsing information
   */
  public ParseContainer(E item, ParseInfo parseInfo)
  {
    this.item = item;
    this.parseInfo = parseInfo;
  }

  /**
   * @return the item
   */
  public E getItem()
  {
    return item;
  }
  /**
   * @return the parsing information
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

}
