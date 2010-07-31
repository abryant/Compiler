package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;

import compiler.language.ast.type.TypeArgument;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {TYPE_ARGUMENT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT};

  public TypeArgumentListRule()
  {
    super(TYPE_ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new TypeArgument[] {(TypeArgument) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      TypeArgument[] oldList = (TypeArgument[]) args[0];
      TypeArgument[] newList = new TypeArgument[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (TypeArgument) args[2];
      return newList;
    }
    throw badTypeList();
  }

}
