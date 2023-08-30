package org.devops

def Build(buildType, buildShell) {
   if (buildType == "mvn") {
     println("build mvn project")
   } else if (buildType == "npm") {
     println("build npm project")
   }
}
