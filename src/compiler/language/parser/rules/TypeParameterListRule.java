package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_PARAMETER;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;

import compiler.language.ast.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {TYPE_PARAMETER};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE_PARAMETER_LIST, COMMA, TYPE_PARAMETER};

  public TypeParameterListRule()
  {
    super(TYPE_PARAMETER_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new TypeParameter[] {(TypeParameter) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      TypeParameter[] oldList = (TypeParameter[]) args[0];
      TypeParameter[] newList = new TypeParameter[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (TypeParameter) args[2];
      return newList;
    }
    throw badTypeList();
  }

}
