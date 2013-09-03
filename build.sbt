import sbtrelease._
import ReleaseStateTransformations._
import ReleasePlugin._
import ReleaseKeys._


name := "statika-distributions"

description := "statika distributions"

homepage := Some(url("https://github.com/ohnosequences/statika-distributions"))

organization := "ohnosequences"

organizationHomepage := Some(url("http://ohnosequences.com"))

licenses += "AGPLv3" -> url("http://www.gnu.org/licenses/agpl-3.0.txt")


// no private plugins so far
privateResolvers := Seq()

publishMavenStyle := false

// for publishing you need to set `s3credentials`
publishTo <<= (isSnapshot, s3credentials) { 
                (snapshot,   credentials) => 
  val prefix = if (snapshot) "snapshots" else "releases"
  credentials map S3Resolver(
      "Era7 "+prefix+" S3 bucket"
    , "s3://"+prefix+".era7.com"
    , Resolver.ivyStylePatterns
    ).toSbtResolver
}

libraryDependencies ++= Seq(
    "ohnosequences" % "statika-cli_2.10.2" % "0.15.1" % "test"
    // "ohnosequences" % "statika-cli_2.10.2" % "0.16.0-SNAPSHOT" % "test"
  // , "ohnosequences" %% "ami-44939930" % "0.8.1"
  , "ohnosequences" %% "ami-44939930" % "0.9.0-SNAPSHOT"
  // members: //
  , "ohnosequences" %% "git" % "0.6.0"
  , "ohnosequences" %% "boost" % "0.1.0"
  , "ohnosequences" %% "bowtie" % "0.1.0"
  , "ohnosequences" %% "tophat" % "0.1.0"
  , "ohnosequences" %% "cufflinks" % "0.1.0"
  , "ohnosequences" %% "python" % "0.1.1"
  , "ohnosequences" %% "s3cmd" % "0.1.0"
  , "ohnosequences" %% "gcc" % "0.1.0"
  , "ohnosequences" %% "zlib-devel" % "0.1.0"
  , "ohnosequences" %% "velvet" % "0.1.1"
  )

// Running test in parallel
testOptions in Test += Tests.Argument("-P12")

// Showing time spent on each test
testOptions in Test += Tests.Argument("-oD")

bundleObjects := Seq("ohnosequences.statika.distributions.AmazonLinux")
