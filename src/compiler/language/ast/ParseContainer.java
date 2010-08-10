package compiler.language.ast;

/*
 * Created on 9 Aug 2010
 */

/**
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
