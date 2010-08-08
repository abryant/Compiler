package compiler.language.ast;

import java.util.LinkedList;
import java.util.List;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 * @param <E> - the type of elements of the list
 */
public class ParseList<E>
{

  private List<E> list;

  private ParseInfo parseInfo;

  /**
   * Creates a new empty ParseList with the specified ParseInfo
   * @param parseInfo - the ParseInfo to store
   */
  public ParseList(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    list = new LinkedList<E>();
  }

  /**
   * Creates a new ParseList containing the specified initial element with the specified ParseInfo
   * @param elem - the initial element for the list
   * @param parseInfo - the ParseInfo to store
   */
  public ParseList(E elem, ParseInfo parseInfo)
  {
    this(parseInfo);
    list.add(elem);
  }

  /**
   * @return the size of this list
   */
  public int size()
  {
    return list.size();
  }

  /**
   * Adds the specified element to the start of the list.
   * @param elem - the element to add
   * @param newInfo - the new ParseInfo for the entire list
   */
  public void addFirst(E elem, ParseInfo newInfo)
  {
    list.add(0, elem);
    parseInfo = newInfo;
  }

  /**
   * Adds the specified element to the end of the list.
   * @param elem - the element to add
   * @param newInfo - the new ParseInfo for the entire list
   */
  public void addLast(E elem, ParseInfo newInfo)
  {
    list.add(elem);
    parseInfo = newInfo;
  }

  /**
   * Sets this list's ParseInfo to the specified ParseInfo object
   * @param parseInfo - the new ParseInfo object for this list
   */
  public void setParseInfo(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
  }

  /**
   * Creates an array of the elements of this list.
   * If <code>array</code> is large enough, the elements will be stored in it, otherwise a new array will be created of the same type.
   * @param array - the array to fill with elements if it is large enough
   * @return an array of all of the elements in this list
   */
  public E[] toArray(E[] array)
  {
    return list.toArray(array);
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

}
