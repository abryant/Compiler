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
}
