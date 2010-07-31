package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.PARAMETER;
import static compiler.language.parser.ParseType.PARAMETER_LIST;

import compiler.language.ast.misc.Parameter;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParameterListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {PARAMETER};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {PARAMETER_LIST, COMMA, PARAMETER};

  public ParameterListRule()
  {
    super(PARAMETER_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new Parameter[] {(Parameter) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Parameter[] oldList = (Parameter[]) args[0];
      Parameter[] newList = new Parameter[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (Parameter) args[2];
      return newList;
    }
    throw badTypeList();
  }

}
