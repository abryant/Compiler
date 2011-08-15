package compiler.language.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import compiler.language.LexicalPhrase;

/*
 * Created on 5 Aug 2010
 */

/**
 * Represents a list of AST nodes which have been parsed.
 * This should only ever be used while parsing, never in the final AST.
 * @author Anthony Bryant
 * @param <E> - the type of elements of the list
 */
public class ParseList<E>
{

  private List<E> list;

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new empty ParseList with the specified LexicalPhrase
   * @param lexicalPhrase - the LexicalPhrase to store
   */
  public ParseList(LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    list = new LinkedList<E>();
  }

  /**
   * Creates a new ParseList containing the specified initial element with the specified LexicalPhrase
   * @param elem - the initial element for the list
   * @param lexicalPhrase - the LexicalPhrase to store
   */
  public ParseList(E elem, LexicalPhrase lexicalPhrase)
  {
    this(lexicalPhrase);
    list.add(elem);
  }

  /**
   * Creates a new ParseList containing the specified list of elements with the specified LexicalPhrase
   * @param elements - the elements in this list
   * @param lexicalPhrase - the lexical phrase to store
   */
  public ParseList(E[] elements, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    list = new ArrayList<E>(elements.length);
    for (E element : elements)
    {
      list.add(element);
    }
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
   * @param newPhrase - the new LexicalPhrase for the entire list
   */
  public void addFirst(E elem, LexicalPhrase newPhrase)
  {
    list.add(0, elem);
    lexicalPhrase = newPhrase;
  }

  /**
   * Adds the specified element to the end of the list.
   * @param elem - the element to add
   * @param newPhrase - the new LexicalPhrase for the entire list
   */
  public void addLast(E elem, LexicalPhrase newPhrase)
  {
    list.add(elem);
    lexicalPhrase = newPhrase;
  }

  /**
   * Sets this list's LexicalPhrase to the specified LexicalPhrase object
   * @param lexicalPhrase - the new LexicalPhrase object for this list
   */
  public void setLexicalPhrase(LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
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
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

}
