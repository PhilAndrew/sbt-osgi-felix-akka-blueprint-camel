//lazy val root = project.in( file(".") ).dependsOn( sbtFelix )

//lazy val sbtFelix = file("../sbt-osgi-felix").toURI

val ivyLocal = Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

externalResolvers := Seq(ivyLocal)

addSbtPlugin("org.doolse" % "sbt-osgi-felix" % "1.0.2-SNAPSHOT")




