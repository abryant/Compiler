package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARRAY_TYPE;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RSQUARE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.ArrayType;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TYPE_NOT_QNAME, LSQUARE, RSQUARE};
  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME, LSQUARE, RSQUARE};

  public ArrayTypeRule()
  {
    super(ARRAY_TYPE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      Type type = (Type) args[0];
      return new ArrayType(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      PointerType pointerType = new PointerType(false, qname.getNames(), typeParameterLists, qname.getParseInfo());
      return new ArrayType(pointerType, ParseInfo.combine(pointerType.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
