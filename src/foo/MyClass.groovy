package foo

import hudson.EnvVars

class MyClass {
  EnvVars env
  def MyClass(env) {
    this.env = env
    // sets somethings not relevant to `script` issue
  }
  def Integer run(script, command) {
    def image = script.docker.image("myimage:tag")
    def run = image.run("-v ${env.WORKSPACE}:/source", command)
    script.sh(
      returnStdout: true,
      script: "docker wait ${run.id}"
    )
  }
}
