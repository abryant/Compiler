package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.COMMA;

import compiler.language.ast.misc.Argument;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ARGUMENT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ARGUMENT_LIST, COMMA, ARGUMENT};

  public ArgumentListRule()
  {
    super(ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new Argument[] {(Argument) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Argument[] oldList = (Argument[]) args[0];
      Argument[] newList = new Argument[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (Argument) args[2];
      return newList;
    }
    throw badTypeList();
  }

}
