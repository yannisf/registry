How to use less
===============
The application uses less for css generation. Essentially, it uses **bootstrap** with a couple of **bootswatch** themes
on top. There are guidelines that should be followed on how apply new styles.

overrides.less
--------------
If the required style can be applied by just changing a bootstrap variable, this should preferred instead of defining 
a new style. These variable overrides should be defined in `overrides.less`.
 
local.less
----------
All custom styles should be defined in this file. Obviously, bootstrap classes and mixins should be reused whenever 
possible.
 
bootswatch.less
---------------
Chooses the bootswatch scheme to compile in.
 
master.less
-----------
Master catalog of all less styles to be compiled. If for some reason a new file is to be introduced, typically this
file should import it.