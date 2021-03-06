package y;

class Foo<A extends x.String super Foo<A>.Bar> // extends Foo<A>.Bar
{
  class Bar extends Foo<Foo<A>.Bar>
  {
    class X extends Foo<Foo<x.String>.Bar.X>.Bar
    {
    }
    int foo;
  }

  void foo(x.String asdf, Foo<SubEnum.B>... vararg, int @i = 17);

  final property #x.String name;

}

class Super
{
  class A
  {
  }

  private class B {}
  protected class C {}
  package interface D {}
  package protected class E {}

}

enum SubEnum extends Super
{
  FOO, BAR;

  class B extends Super.A
  {
  }

  class Z extends Super.C {}
  interface Y extends Super.D {}
  class X extends Super.E {}
}
