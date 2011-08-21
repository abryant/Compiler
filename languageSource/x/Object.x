package x;

// NOTE: this file currently can NOT have any imports.
// This is because imports need to be checked by an AccessSpecifierChecker,
// but it cannot be created until the universal base class (x.Object) has been loaded,
// or it would not be able to check protected members.

public class Object
{
  native("x_Object_equals") bool equals(Object other);
  native("x_Object_hashCode") int hashCode();
}
