package test.language;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.parser.LanguageParser;
import compiler.language.translator.conceptual.ConceptualTranslator;

/*
 * Created on 30 Apr 2011
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualTranslationTest
{

  public static void main(String[] args) throws NameConflictException, ConceptualException
  {
    if (args.length < 2)
    {
      System.err.println("Usage: java test.language.ConceptualTranslationTest <SourceFile> <ClasspathFolder>");
      System.exit(1);
    }

    File file = new File(args[0]);
    if (!file.isFile())
    {
      System.err.println("Could not find source file: " + file.getAbsolutePath());
      System.exit(1);
    }
    List<File> classpath = new LinkedList<File>();
    classpath.add(new File(args[1]));

    LanguageParser parser = new LanguageParser();

    ConceptualTranslator translator = new ConceptualTranslator(parser, classpath);

    ConceptualFile conceptualFile = translator.parseFile(file);

    if (conceptualFile != null)
    {
      System.out.println("Successfully parsed " + file.getAbsolutePath());
      System.out.println(conceptualFile);

      translator.translate();

      System.out.println("Successfully translated");
      System.out.println("Classes: ");
      for (ConceptualClass conceptualClass : conceptualFile.getClasses())
      {
        System.out.println("  " + conceptualClass.getName());
        System.out.println("  extends: " + conceptualClass.getBaseClass().getTypeInstance());
      }

    }
  }

}
