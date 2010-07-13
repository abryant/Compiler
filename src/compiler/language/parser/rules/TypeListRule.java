package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.Type;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {TYPE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE_LIST, COMMA, TYPE};

  public TypeListRule()
  {
    super(TYPE_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new Type[] {(Type) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Type[] oldList = (Type[]) args[0];
      Type[] newList = new Type[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (Type) args[2];
      return newList;
    }
    throw badTypeList();
  }

}
