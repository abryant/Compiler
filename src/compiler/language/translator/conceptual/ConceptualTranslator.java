package compiler.language.translator.conceptual;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.UnresolvableException;
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
   * @param classpath - the classpath for this translator
   */
  public ConceptualTranslator(LanguageParser parser, List<File> classpath)
  {
    this.parser = parser;
    rootPackage = new ConceptualPackage(this, classpath);
    astConverter = new ASTConverter(rootPackage);
    nameResolver = new NameResolver(astConverter.getConceptualASTNodes());
    nameResolver.initialize(rootPackage);
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
   * Resolves the specified QName on the root package
   * @param name - the name to resolve
   * @return the Resolvable resolved
   * @throws NameConflictException - if a name conflict was detected
   * @throws UnresolvableException - if the name is not resolvable without more information
   */
  public Resolvable translate(QName name) throws NameConflictException, UnresolvableException
  {
    return rootPackage.resolve(name, false);
  }

  /**
   * Attempts to parse the specified file into a conceptual file. If something fails in the process, an error is printed before returning null.
   * If a file is correctly parsed and converted into a ConceptualFile, it is also added to the NameResolver.
   * @param file - the file to parse
   * @param expectedPackage - the package that the file is expected to be in, which should be checked against the package declaration inside it
   * @return the ConceptualFile parsed, or null if an error occurred
   */
  public ConceptualFile parseFile(File file, ConceptualPackage expectedPackage)
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

    if (ast == null)
    {
      return null;
    }

    try
    {
      ConceptualFile conceptualFile = astConverter.convert(file, ast, expectedPackage);
      nameResolver.addFile(conceptualFile);
      return conceptualFile;
    }
    catch (ConceptualException e)
    {
      printConceptualException(e.getMessage(), e.getParseInfo());
      return null;
    }
    catch (NameConflictException e)
    {
      printConceptualException(e.getMessage() == null ? "Name conflict detected" : e.getMessage(), e.getParseInfo());
      return null;
    }
  }

  private static void printConceptualException(String message, ParseInfo... parseInfos)
  {
    if (parseInfos == null || parseInfos.length < 1)
    {
      System.err.println(message);
      return;
    }
    // make a String representation of the ParseInfos' character ranges
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < parseInfos.length; i++)
    {
      // line:start-end
      buffer.append(parseInfos[i].getLocationText());
      if (i != parseInfos.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (parseInfos.length == 1)
    {
      System.err.println(buffer + ": " + message);
      System.err.println(parseInfos[0].getHighlightedLine());
    }
    else
    {
      System.err.println(buffer + ": " + message);
      for (ParseInfo info : parseInfos)
      {
        System.err.println(info.getHighlightedLine());
      }
    }
  }

}
