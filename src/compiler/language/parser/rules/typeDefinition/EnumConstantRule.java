package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Parameter;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.typeDefinition.EnumConstant;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {NAME};
  private static final Object[] PARAMETERS_PRODUCTION = new Object[] {NAME, PARAMETERS};

  public EnumConstantRule()
  {
    super(ENUM_CONSTANT, PRODUCTION, PARAMETERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      Name name = (Name) args[0];
      return new EnumConstant(name, new Parameter[0], name.getParseInfo());
    }
    if (types == PARAMETERS_PRODUCTION)
    {
      Name name = (Name) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[1];
      return new EnumConstant(name, parameters.toArray(new Parameter[0]), ParseInfo.combine(name.getParseInfo(), parameters.getParseInfo()));
    }
    throw badTypeList();
  }

}
