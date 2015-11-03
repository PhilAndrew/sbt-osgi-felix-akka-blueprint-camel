//lazy val root = project.in( file(".") ).dependsOn( sbtFelix )

//lazy val sbtFelix = file("../sbt-osgi-felix").toURI

//val ivyLocal = Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
//externalResolvers := Seq(ivyLocal)

def sbtOsgiFelix = ProjectRef(
  uri("git://github.com/PhilAndrew/sbt-osgi-felix.git"),
  "sbt-osgi-felix")

lazy val plugins = (project in file("."))
  .dependsOn(sbtOsgiFelix)





