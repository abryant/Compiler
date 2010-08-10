package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;

import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TYPE_NOT_QNAME};
  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME};

  public TypeRule()
  {
    super(TYPE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    if (types == QNAME_PRODUCTION)
    {
      // create a new PointerType from the QName
      QName qname = (QName) args[0];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParams = new TypeParameter[names.length][];
      for (int i = 0; i < typeParams.length; i++)
      {
        typeParams[i] = new TypeParameter[0];
      }
      return new PointerType(false, names, typeParams, qname.getParseInfo());
    }
    throw badTypeList();
  }

}
