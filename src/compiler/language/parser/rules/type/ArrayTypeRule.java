package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARRAY_TYPE;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RSQUARE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.ArrayTypeAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArrayTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 2L;

  private static final Production<ParseType> PRODUCTION                        = new Production<ParseType>(TYPE_NOT_QNAME_LIST,       LSQUARE, RSQUARE);
  private static final Production<ParseType> QNAME_PRODUCTION                  = new Production<ParseType>(QNAME,                     LSQUARE, RSQUARE);
  private static final Production<ParseType> NESTED_QNAME_PRODUCTION           = new Production<ParseType>(NESTED_QNAME_LIST,         LSQUARE, RSQUARE);
  private static final Production<ParseType> IMMUTABLE_PRODUCTION              = new Production<ParseType>(TYPE_NOT_QNAME_LIST, HASH, LSQUARE, RSQUARE);
  private static final Production<ParseType> IMMUTABLE_QNAME_PRODUCTION        = new Production<ParseType>(QNAME,               HASH, LSQUARE, RSQUARE);
  private static final Production<ParseType> IMMUTABLE_NESTED_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST,   HASH, LSQUARE, RSQUARE);

  @SuppressWarnings("unchecked")
  public ArrayTypeRule()
  {
    super(ARRAY_TYPE, PRODUCTION,           QNAME_PRODUCTION,           NESTED_QNAME_PRODUCTION,
                      IMMUTABLE_PRODUCTION, IMMUTABLE_QNAME_PRODUCTION, IMMUTABLE_NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      return new ArrayTypeAST(type, false, LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2]));
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      return new ArrayTypeAST(new PointerTypeAST(qname, qname.getLexicalPhrase()), false, LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2]));
    }
    if (NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST baseType = element.toType();
      return new ArrayTypeAST(baseType, false, LexicalPhrase.combine(element.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2]));
    }
    if (IMMUTABLE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      return new ArrayTypeAST(type, true, LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2], (LexicalPhrase) args[3]));
    }
    if (IMMUTABLE_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      return new ArrayTypeAST(new PointerTypeAST(qname, qname.getLexicalPhrase()), true, LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2], (LexicalPhrase) args[3]));
    }
    if (IMMUTABLE_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST baseType = element.toType();
      return new ArrayTypeAST(baseType, true, LexicalPhrase.combine(element.getLexicalPhrase(), (LexicalPhrase) args[1], (LexicalPhrase) args[2], (LexicalPhrase) args[3]));
    }
    throw badTypeList();
  }

}
