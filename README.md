# microbase

Microbase is a utility library allowing developers to create plugins for Minecraft servers that are platform-independent.
It implements a number of `Base` types that abstract away platform-specific details (mostly).

It is recommended to access the static `Microbase.get`ters after calling `Microbase.setup()`.

I don't like writing documentation, so I recommend looking at the classes and interfaces in the `com.danifoldi.microbase` package (and IDE code completion). _If you do, all PRs are welcome._
