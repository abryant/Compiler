package compiler.language.translator.conceptual;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.topLevel.ConceptualPackage;
import compiler.language.parser.LanguageParser;
import compiler.language.parser.LanguageTokenizer;

/*
 * Created on 14 Nov 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualTranslator
{

  private LanguageParser parser;
  private ConceptualPackage rootPackage;
  private ASTConverter astConverter;
  private NameResolver nameResolver;

  /**
   * Creates a new ConceptualTranslator to parse and convert files to the conceptual hierarchy.
   * @param parser - the LanguageParser to use to parse files
   */
  public ConceptualTranslator(LanguageParser parser)
  {
    this.parser = parser;
    rootPackage = new ConceptualPackage(this);
    astConverter = new ASTConverter(rootPackage);
    nameResolver = new NameResolver(astConverter.getConceptualASTNodes());
  }

  /**
   * Translates the files which have been parsed into the conceptual representation.
   * @throws NameConflictException - if a name conflict was detected
   * @throws ConceptualException - if a conceptual problem was detected during the translation
   */
  public void translate() throws NameConflictException, ConceptualException
  {
    nameResolver.resolveParents();
  }

  /**
   * Attempts to parse the specified file into a conceptual file. If something fails in the process, an error is printed before returning null.
   * If a file is correctly parsed and converted into a ConceptualFile, it is also added to the NameResolver.
   * @param file - the file to parse
   * @return the ConceptualFile parsed, or null if an error occurred
   */
  public ConceptualFile parseFile(File file)
  {
    CompilationUnitAST ast;
    try
    {
      FileReader reader = new FileReader(file);
      LanguageTokenizer tokenizer = new LanguageTokenizer(reader);
      ast = parser.parse(tokenizer);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      return null;
    }

    try
    {
      ConceptualFile conceptualFile = astConverter.convert(ast);
      nameResolver.addFile(conceptualFile);
      return conceptualFile;
    }
    catch (ConceptualException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (NameConflictException e)
    {
      e.printStackTrace();
      return null;
    }
  }

}
