package compiler.language.parser;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.expression.ParenthesisedExpressionAST;
import compiler.language.ast.expression.TupleExpressionAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TupleTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;

/*
 * Created on 17 Aug 2010
 */

/**
 * A class which holds either a QNameAST or a nested list of QNameElementASTs.
 * This should only be used during parsing, and should never occur in the final
 * AST.
 * @author Anthony Bryant
 */
public class QNameElementAST
{

  private QNameAST qname = null;
  private ParseList<QNameElementAST> elements = null;
  private LexicalPhrase listLexicalPhrase = null;

  /**
   * Creates a new QNameElementAST to store the specified QNameAST
   * @param qname - the QNameAST to store
   */
  public QNameElementAST(QNameAST qname)
  {
    this.qname = qname;
  }

  /**
   * Creates a new QNameElementAST to store the specified list of QNameElements
   * @param elements - the list of QNameElements to store
   * @param listLexicalPhrase - the LexicalPhrase for the list this represents, including the surrounding brackets
   */
  public QNameElementAST(ParseList<QNameElementAST> elements, LexicalPhrase listLexicalPhrase)
  {
    this.listLexicalPhrase = listLexicalPhrase;
    this.elements = elements;
  }

  /**
   * @return the LexicalPhrase for this QNameElementAST
   */
  public LexicalPhrase getLexicalPhrase()
  {
    if (qname != null)
    {
      return qname.getLexicalPhrase();
    }
    return listLexicalPhrase;
  }

  /**
   * Converts this QNameElementAST into either a FieldAccessExpressionAST or a ParenthesisedExpressionAST containing a TupleExpressionAST
   * @return the resulting ExpressionAST
   */
  public ExpressionAST toExpression()
  {
    if (qname != null)
    {
      return new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
    }

    QNameElementAST[] elems = elements.toArray(new QNameElementAST[0]);
    ExpressionAST[] expressions = new ExpressionAST[elems.length];
    for (int i = 0; i < elems.length; i++)
    {
      expressions[i] = elems[i].toExpression();
    }
    TupleExpressionAST expression = new TupleExpressionAST(expressions, elements.getLexicalPhrase());

    return new ParenthesisedExpressionAST(expression, listLexicalPhrase);
  }

  /**
   * Converts this QNameElementAST into either a PointerTypeAST or a TupleTypeAST.
   * @return the resulting TypeAST
   */
  public TypeAST toType()
  {
    if (qname != null)
    {
      NameAST[] names = qname.getNames();
      TypeArgumentAST[][] typeArgumentLists = new TypeArgumentAST[names.length][];
      return new PointerTypeAST(false, qname.getNames(), typeArgumentLists, qname.getLexicalPhrase());
    }

    QNameElementAST[] elems = elements.toArray(new QNameElementAST[0]);
    TypeAST[] types = new TypeAST[elems.length];
    for (int i = 0; i < elems.length; i++)
    {
      types[i] = elems[i].toType();
    }
    return new TupleTypeAST(types, listLexicalPhrase);
  }

}
