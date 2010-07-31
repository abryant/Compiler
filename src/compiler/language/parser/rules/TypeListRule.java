package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeListRule extends Rule
{

  // this is a right-recursive list, so that the symbol after the list can be
  // used to decide between TypeList and AssigneeList
  private static final Object[] START_PRODUCTION = new Object[] {TYPE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE, COMMA, TYPE_LIST};

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
      Type[] oldList = (Type[]) args[2];
      Type[] newList = new Type[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 1, oldList.length);
      newList[0] = (Type) args[0];
      return newList;
    }
    throw badTypeList();
  }

}
