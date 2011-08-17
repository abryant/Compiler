package test.language;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.UnresolvableException;
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

  public static void main(String[] args)
  {
    if (args.length < 2)
    {
      System.err.println("Usage: java test.language.ConceptualTranslationTest <Class> <ClasspathFolder>");
      System.exit(1);
    }

    /*
    File file = new File(args[0]);
    if (!file.isFile())
    {
      System.err.println("Could not find source file: " + file.getAbsolutePath());
      System.exit(1);
    }
    */
    List<File> classpath = new LinkedList<File>();
    classpath.add(new File(args[1]));

    LanguageParser parser = new LanguageParser();

    ConceptualTranslator translator = new ConceptualTranslator(parser, classpath);

    // extract the names and the lexical phrases for them from args[0]
    List<String> namesList = new LinkedList<String>();
    List<LexicalPhrase> lexicalPhrasesList = new LinkedList<LexicalPhrase>();
    String remaining = args[0];
    int column = 0;
    while (remaining.length() > 0)
    {
      String name;
      if (remaining.indexOf('.') == -1)
      {
        name = remaining;
        remaining = "";
      }
      else
      {
        name = remaining.substring(0, remaining.indexOf('.'));
        remaining = remaining.substring(name.length() + 1);
      }
      LexicalPhrase lexicalPhrase = new LexicalPhrase(1, args[0], column, column + name.length());
      column += name.length() + 1;
      namesList.add(name);
      lexicalPhrasesList.add(lexicalPhrase);
    }
    String[] names = namesList.toArray(new String[0]);
    LexicalPhrase[] lexicalPhrases = lexicalPhrasesList.toArray(new LexicalPhrase[0]);

    try
    {
      Resolvable resolved = translator.translate(new QName(names, lexicalPhrases));
      if (resolved == null || resolved.getType() != ScopeType.OUTER_CLASS)
      {
        System.out.println("\"" + args[0] + "\" could not be resolved to a class");
        return;
      }
      ConceptualClass conceptualClass = (ConceptualClass) resolved;
      System.out.println("Translated: " + conceptualClass);

      translator.translate();
    }
    catch (NameConflictException e)
    {
      printConceptualException(e.getMessage(), e.getLexicalPhrases());
      return;
    }
    catch (UnresolvableException e)
    {
      printConceptualException(e.getMessage(), e.getLexicalPhrase());
      return;
    }
    catch (ConceptualException e)
    {
      printConceptualException(e.getMessage(), e.getLexicalPhrases());
      return;
    }

    System.out.println("Successfully translated");
  }

  private static void printConceptualException(String message, LexicalPhrase... lexicalPhrases)
  {
    if (lexicalPhrases == null || lexicalPhrases.length < 1)
    {
      System.err.println(message);
      return;
    }
    // make a String representation of the LexicalPhrases' character ranges
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < lexicalPhrases.length; i++)
    {
      // line:start-end
      buffer.append(lexicalPhrases[i].getLocationText());
      if (i != lexicalPhrases.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (lexicalPhrases.length == 1)
    {
      System.err.println(buffer + ": " + message);
      System.err.println(lexicalPhrases[0].getHighlightedLine());
    }
    else
    {
      System.err.println(buffer + ": " + message);
      for (LexicalPhrase phrase : lexicalPhrases)
      {
        System.err.println(phrase.getHighlightedLine());
      }
    }
  }

}
